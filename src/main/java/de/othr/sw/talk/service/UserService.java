package de.othr.sw.talk.service;

import de.othr.sw.talk.entity.User;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

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
        //TODO: delete every posting of this user or there will be a error!
        em.remove(user);
    }
    
    public User authenticate(String username, String password){
        User u = em.find(User.class, username);
        if(u != null && u.getPassword().equals(password)){
            return u;
        }
        return null;
    }
    
    public User getUserById(String username){
        return em.find(User.class, username);
    }
    
    public List<User> getAllUser() {
        TypedQuery<User> query = em.createQuery("SELECT u FROM User AS u", User.class);
        return query.getResultList();
    }

    public boolean checkIfUsernameIsFree(String username) {
        if(em.find(User.class, username) != null){
            return false;
        }
        return true;
   }
    
}
