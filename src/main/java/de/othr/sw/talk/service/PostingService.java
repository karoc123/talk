package de.othr.sw.talk.service;

import de.othr.sw.talk.entity.Category;
import de.othr.sw.talk.entity.Posting;
import de.othr.sw.talk.entity.User;
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
        TypedQuery<Posting> query = em.createQuery("SELECT u FROM Posting AS u", Posting.class);
        return query.getResultList();
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
}
