package de.othr.sw.talk.model;

import de.othr.sw.talk.entity.User;
import de.othr.sw.talk.service.UserService;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * To create or display a user
 * @see LoginModel
 * @author Karoc
 */
@Named
@SessionScoped
public class UserModel implements Serializable{
    
    @Inject
    private UserService userService;
    
    private String username, password;
    private User lastGenerated = null;

    public void create(){
        this.lastGenerated = userService.createUser(new User(this.username, this.password));
        this.username = null;
        this.password = null;
    }
    
    public List<User> allActiveUsers() {
        return this.userService.getAllActiveUsers();
    }
    
    public void delete(User toDelete){
        this.userService.deleteUser(toDelete);
    }
  
    public void deactivate(User toDelete){
        this.userService.deactivateUser(toDelete);
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getLastGenerated() {
        return lastGenerated;
    }

    public void setLastGenerated(User lastGenerated) {
        this.lastGenerated = lastGenerated;
    }
    
    
}
