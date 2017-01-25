package de.othr.sw.talk.service;

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
}
