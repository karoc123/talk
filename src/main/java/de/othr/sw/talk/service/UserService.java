package de.othr.sw.talk.service;

import de.othr.sw.talk.entity.User;
import de.othr.sw.talk.entity.UserInformation;
import de.othr.sw.talk.entity.util.EntityUtils;
import java.util.List;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

@RequestScoped
public class UserService {
    
    @Inject
    private Logger log;
    
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
        if(u != null){
            try {
                if( EntityUtils.hashPassword(password, u.getSalt(), User.HASH_ALGORITHM).equals(u.getPassword()) )
                    return u;
                else 
                    return null;
            } catch (EntityUtils.EntityUtilException ex) {
                throw new RuntimeException("password can not be hashed", ex);
            }
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

    @Transactional
    public void changeEmail(String email, User user) {
        user = em.merge(user);
        if(user.getUserInformation() == null){
            user.setUserInformation(new UserInformation());
        }
        user.getUserInformation().setEmail(email);
    }

    @Transactional
    public boolean changePassword(String password, String newPassword, User user) {
        user = em.merge(user);
        if(user != null){
            try {
                if( EntityUtils.hashPassword(password, user.getSalt(), User.HASH_ALGORITHM).equals(user.getPassword()) ){
                    // Old password is correct
                    user.setPassword(EntityUtils.hashPassword(newPassword, user.getSalt(), User.HASH_ALGORITHM));
                    log.info("Password changed of: " + user.toString() );
                    return true;
                }
                else  {
                    return false;
                }
            } catch (EntityUtils.EntityUtilException ex) {
                throw new RuntimeException("password can not be hashed", ex);
            }
        }
        return false;
    }
    
}
