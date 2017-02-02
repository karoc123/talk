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

/**
 * CRUD for users
 */
@RequestScoped
public class UserService {
    
    @Inject
    private Logger log;
    
    @PersistenceContext(unitName="TalkPU")
    private EntityManager em;
    
    /**
     * Persists a new user
     * @param newUser
     * @return 
     */
    @Transactional
    public User createUser(User newUser){
        em.persist(newUser);
        return newUser;
    }
    
    // NOT USED AT THE MOMENT
    @Transactional(TxType.REQUIRED)
    public void deleteUser(User toDelete){
        User user = em.merge(toDelete);
        //TODO: delete every posting of this user or there will be a error!
        em.remove(user);
    }
    
    /**
     * deactivate a new user, the user can not login anymore
     * @param toDelete 
     */
    @Transactional
    public void deactivateUser(User toDelete) {
        User user = em.merge(toDelete);
        user.setIsActiv(false);
        em.persist(user);
    }
    
    /**
     * Test if the user is active with the given password
     * @param username
     * @param password
     * @return the user, if valid
     */
    public User authenticate(String username, String password){
        User u = em.find(User.class, username);
        if(u != null){
            try {
                if( EntityUtils.hashPassword(password, u.getSalt(), User.HASH_ALGORITHM).equals(u.getPassword()) 
                        && u.isIsActiv())
                    return u;
                else 
                    return null;
            } catch (EntityUtils.EntityUtilException ex) {
                throw new RuntimeException("password can not be hashed", ex);
            }
        }
        return null;
    }
    
    /**
     * Get user object by username
     * @param username
     * @return 
     */
    public User getUserById(String username){
        return em.find(User.class, username);
    }
    
    /**
     * Get every user (incl. inactive)
     * @return 
     */
    public List<User> getAllUser() {
        TypedQuery<User> query = em.createQuery("SELECT u FROM User AS u", User.class);
        return query.getResultList();
    }
    
    /**
     * Get every user (excl. inactive)
     * @return 
     */
    public List<User> getAllActiveUsers() {
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.isActiv = true", User.class);
        return query.getResultList();
    }

    /**
     * True, if the given username is not in use
     * @param username
     * @return 
     */
    public boolean checkIfUsernameIsFree(String username) {
        if(em.find(User.class, username) != null){
            return false;
        }
        return true;
   }

    /**
     * Creates / Changes a email for the given user
     * @param email
     * @param user 
     */
    @Transactional
    public void changeEmail(String email, User user) {
        user = em.merge(user);
        if(user.getUserInformation() == null){
            user.setUserInformation(new UserInformation());
        }
        user.getUserInformation().setEmail(email);
    }

    /**
     * Changes password to new password if the old password is the same as on the user
     * @param password
     * @param newPassword
     * @param user
     * @return true if password is changed
     */
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
