package de.othr.sw.talk.service;

import de.othr.sw.talk.entity.User;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

/**
 *
 * @author karoc
 */
@RequestScoped
public class UserService {
    
    @PersistenceContext(unitName="TalkPU")
    private EntityManager em;
    
    @Transactional
    public User createUser(User newUser){
        em.persist(newUser);
        return newUser;
    }
    
    @Transactional(TxType.REQUIRED)
    public void deleteUser(User toDelete){
        User user = em.merge(toDelete);
        em.remove(user);
    }
    
    public boolean authenticate(String username, String password){
        
        return false;
    }
    
    public List<User> getAllUser() {
        TypedQuery<User> query = em.createQuery("SELECT u FROM User AS u", User.class);
        return query.getResultList();
    }
    
}
