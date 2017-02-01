package de.othr.sw.talk.service;

import de.othr.sw.talk.entity.Category;
import de.othr.sw.talk.entity.Comment;
import de.othr.sw.talk.entity.Posting;
import de.othr.sw.talk.entity.User;
import de.othr.sw.talk.entity.Vote;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.transaction.Transactional;

@RequestScoped
public class PostingService {
    
    @Inject
    private Logger log;
    
    @PersistenceContext(unitName="TalkPU")
    private EntityManager em;
    
    @Transactional
    public Posting createPosting(Posting newPosting){
        em.persist(newPosting);
        return newPosting;
    }
    
    public List<Posting> getAllPostings() {
        TypedQuery<Posting> query = em.createQuery("SELECT u FROM Posting AS u ORDER BY u.creationDate DESC", Posting.class);
        return query.getResultList();
    }
    
    public List<Posting> getAllPostings(int postingPaginationStart, int count) {
        TypedQuery<Posting> query;
        query = em.createQuery(
                "SELECT u FROM Posting AS u ORDER BY u.creationDate DESC",
                Posting.class);
        return query
                .setMaxResults(count)
                .setFirstResult(postingPaginationStart)
                .getResultList();
    }
    
    public Long getNumberOfPostingsForAll() {
        CriteriaBuilder qb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = qb.createQuery(Long.class);
        cq.select(qb.count(cq.from(Posting.class)));
        ParameterExpression<Integer> p = qb.parameter(Integer.class);
        return em.createQuery(cq).getSingleResult();
   }
 
    public List<Posting> getAllPostingsByCategory(String category) {
        Category cat = this.getCategoryByString(category);
        TypedQuery<Posting> query;
        query = em.createQuery("SELECT u FROM Posting u WHERE u.category = :category ORDER BY u.creationDate DESC", Posting.class);
        return query.setParameter("category", cat).getResultList();
    }
    
    @Transactional
    public Category createCategory(Category newCategory){
        if(em.find(Category.class, newCategory.toString()) == null){
            em.persist(newCategory);
            return newCategory;
        } else {
            return null;
        }
    }
    
    public Category getCategoryByString(String cat){
        return em.find(Category.class, cat);
    }
    
    public List<Category> getAllCategorys() {
        TypedQuery<Category> query = em.createQuery("SELECT u FROM Category AS u", Category.class);
        return query.getResultList();
    }
    
    /**
     * If the category still has postings, they will all be deleted
     * @param toDelete
     * @return 
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public boolean deleteCategory(Category toDelete){
        Category category = em.merge(toDelete);
        
        Query query;
        query = em.createQuery("DELETE FROM Posting u WHERE u.category = :category");
        query.setParameter("category", toDelete).executeUpdate();
        
        try {
            em.remove(category);
            log.info("Category deleted");
            return true;
        } catch(Exception e) {
            log.info("Category delete error!");
            return false;
        }
    }

    public Posting getPostingById(long id) {
        Posting post = em.find(Posting.class, id);
        return post;
    }

    @Transactional
    public Comment createComment(Posting posting, Comment newComment){
        posting = em.merge(posting);
        if(newComment.getParent() != null){
            Comment parent = em.find(Comment.class, newComment.getParent().getId());
            if(parent != null){
                parent.addCommentToChildList(newComment);
            }
            em.persist(parent);            
        }
        posting.getComments().add(newComment);
        em.persist(posting);
        em.persist(newComment);
        return newComment;
    }

    public Comment getCommentById(long parentCommentId) {
        Comment comment = em.find(Comment.class, parentCommentId);
        return comment;
    }
    
    public List<Comment> getAllCommentsByPostingId(long postingId) {
        Posting post = this.getPostingById(postingId);
        return post.getComments();
    }

    public List<Comment> getCommentChilds(Comment com) {
        TypedQuery<Comment> query;
        query = em.createQuery("SELECT u FROM Comment u WHERE u.parent = :parent ORDER BY u.creationDate DESC", Comment.class);
        return query.setParameter("parent", com).getResultList();        
    }

    public List<Comment> getallRootCommentsForPosting(long postingId) {
        Posting post = this.getPostingById(postingId);
        post.getComments().sort((c1, c2) -> c1.compareTo(c2));
        return post.getComments().stream()
            .filter(p -> null == p.getParent())
            .collect(Collectors.toList());     
   }

    public List<Comment> allChildCommentsForPosting(long postingId) {
        Posting post = this.getPostingById(postingId);
        return post.getComments().stream()
            .filter(p -> p.getParent() != null)
            .collect(Collectors.toList());
    }

    @Transactional
    public void updateVotePosting(Posting post, User user, boolean vote) {
        post = em.merge(post);
        
        if(post.getVote() == null){
            post.setVote(new HashSet<>());
        }
        
        //check if voted, if not create vote
        Set<Vote> votes = post.getVote();
        if(!votes.stream().anyMatch((v) -> (v.getUser().equals(user)))){
            //create new vote
            Vote newVote = new Vote(vote, user);
            post.getVote().add(newVote);
            newVote.setUser(em.merge(newVote.getUser()));
            em.persist(post);
            em.persist(newVote);
            
            if(vote){
                post.setVoting(post.getVoting()+1);
            } else {
                post.setVoting(post.getVoting()-1);
            }
        } else {
            //already voted, change vote
            Optional<Vote> newVote;
            newVote = votes.stream()
                    .filter(((v) -> (v.getUser().equals(user))))
                    .findFirst();
            
            // change vote on posting
            if(newVote.get().isVote() != vote){
               if(vote){
                   post.setVoting(post.getVoting()+2);
               } else {
                   post.setVoting(post.getVoting()-2);
               }               
            }

            newVote.get().setVote(vote);
            em.persist(post);
            em.persist(newVote.get());
        }
    }

    /**
     * If the user voted on the post, return = true
     * @param post
     * @param user
     * @return 
     */
    @Transactional
    public boolean checkIfUserVoted(Posting post, User user){
        Set<Vote> votes = post.getVote();
        return votes.stream().anyMatch((v) -> (v.getUser().equals(user)));
    }

    /**
     * All postings from one user
     * @param user
     * @return 
     */
    public List<Posting> getAllPostings(User user) {
        TypedQuery<Posting> query;
        query = em.createQuery("SELECT u FROM Posting u WHERE u.user = :user ORDER BY u.creationDate DESC", Posting.class);
        return query.setParameter("user", user).getResultList();
   }

    /**
     * Delete a posting, if user = post.user OR user = admin
     * @param post
     * @param user
     * @return
     */
    @Transactional
    public boolean delete(Posting post, User user) {
        post = em.merge(post);
        user = em.merge(user);
        
        if(user.isIsAdmin()){
            em.remove(post);
            log.info("Posting deleted");
            return true;
        }
        log.info("Something went wrong on posting delete");
        return false;
   }
    
}
