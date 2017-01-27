package de.othr.sw.talk.service;

import de.othr.sw.talk.entity.Category;
import de.othr.sw.talk.entity.Comment;
import de.othr.sw.talk.entity.Posting;
import de.othr.sw.talk.entity.User;
import de.othr.sw.talk.entity.Vote;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

@RequestScoped
public class PostingService {
    
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
 
    public List<Posting> getAllPostingsByCategory(String category) {
        Category cat = this.getCategoryByString(category);
        TypedQuery<Posting> query;
        query = em.createQuery("SELECT u FROM Posting u WHERE u.category = :category ORDER BY u.creationDate DESC", Posting.class);
        return query.setParameter("category", cat).getResultList();
    }
    
    @Transactional
    public Category createCategory(Category newCategory){
        em.persist(newCategory);
        return newCategory;
    }
    
    public Category getCategoryByString(String cat){
        return em.find(Category.class, cat);
    }
    
    public List<Category> getAllCategorys() {
        TypedQuery<Category> query = em.createQuery("SELECT u FROM Category AS u", Category.class);
        return query.getResultList();
    }
    
    @Transactional(Transactional.TxType.REQUIRED)
    public void deleteCategory(Category toDelete){
        Category category = em.merge(toDelete);
        //TODO: delete every posting of this category or there will be a error!
        em.remove(category);
    }

    public Posting getPostingById(long id) {
        Posting post = em.find(Posting.class, id);
        return post;
    }

    @Transactional
    public Comment createComment(Comment newComment){
        if(newComment.getParent() != null){
            Comment parent = em.find(Comment.class, newComment.getParent().getId());
            if(parent != null){
                parent.addCommentToChildList(newComment);
            }
            em.persist(parent);            
        }
        em.persist(newComment);
        return newComment;
    }

    public Comment getCommentById(long parentCommentId) {
        Comment comment = em.find(Comment.class, parentCommentId);
        return comment;
    }
    
    public List<Comment> getAllCommentsByPostingId(long postingId) {
        Posting post = this.getPostingById(postingId);
        TypedQuery<Comment> query;
        query = em.createQuery("SELECT u FROM Comment u WHERE u.posting = :posting ORDER BY u.creationDate DESC", Comment.class);
        return query.setParameter("posting", post).getResultList();
    }

    public List<Comment> getCommentChilds(Comment com) {
        TypedQuery<Comment> query;
        query = em.createQuery("SELECT u FROM Comment u WHERE u.parent = :parent ORDER BY u.creationDate DESC", Comment.class);
        return query.setParameter("parent", com).getResultList();        
    }

    public List<Comment> getallRootCommentsForPosting(long postingId) {
        Posting post = this.getPostingById(postingId);
        TypedQuery<Comment> query;
        query = em.createQuery("SELECT u FROM Comment u WHERE u.posting = :posting AND u.parent IS NULL ORDER BY u.creationDate DESC", Comment.class);
        return query.setParameter("posting", post).getResultList();       
   }

    public List<Comment> allChildCommentsForPosting(long postingId) {
        Posting post = this.getPostingById(postingId);
        TypedQuery<Comment> query;
        query = em.createQuery("SELECT u FROM Comment u WHERE u.posting = :posting AND u.parent IS NOT NULL ORDER BY u.creationDate DESC", Comment.class);
        return query.setParameter("posting", post).getResultList();      }

    @Transactional
    public void upVotePosting(Posting post, Vote newVote) {
        post = em.merge(post);
        post.setVoting(post.getVoting()+1);
        if(post.getVote() == null){
            post.setVote(new HashSet<Vote>());
        }
        post.getVote().add(newVote);
        newVote.setUser(em.merge(newVote.getUser()));
        em.persist(post);
        em.persist(newVote);
    }
}
