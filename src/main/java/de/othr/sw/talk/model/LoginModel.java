
package de.othr.sw.talk.model;

import de.othr.sw.talk.entity.User;
import de.othr.sw.talk.service.SeedingService;
import de.othr.sw.talk.service.UserService;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@SessionScoped
public class LoginModel implements Serializable{
    @Inject
    private UserService userService;

    @Inject
    private SeedingService seedingService;
    
    @Inject
    private MessageModel messageModel;
    
    private boolean isAuthenticated = false;
    
    private String username, password, errors;

    private User user;

    public LoginModel() {
    }

    @PostConstruct
    public void testdata() {
        seedingService.generateTestdata();
    }
    
    public User getUser() {
        return user;
    }

    public void setUser(User u) {
        this.user = u;
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

    public String getErrors() {
        return errors;
    }

    public void setErrors(String errors) {
        this.errors = errors;
    }

    public boolean isIsAuthenticated() {
        return isAuthenticated;
    }

    public void setIsAuthenticated(boolean isAuthenticated) {
        this.isAuthenticated = isAuthenticated;
    }
    
    public String authenticate(){
        user = this.userService.authenticate(this.username, this.password);
        if (user != null){
            this.isAuthenticated = true;
            this.messageModel.cleanOutMessages();
            return "home.xhtml";
        } else {
            this.isAuthenticated = false;
            this.messageModel.cleanOutMessages();
            this.messageModel.addMessage("Username or password wrong!");
            return "login.xhtml";
        }
    }
 
    public String logout(){
        this.isAuthenticated = false;
        this.user = null;
        this.username = "";
        this.password = "";
        return "home.xhtml";
    }
    
    public String register(){
         if (this.userService.checkIfUsernameIsFree(this.username)){
            this.user = this.userService.createUser(new User(this.username, this.password));
            this.isAuthenticated = true;
            this.messageModel.cleanOutMessages();
            return "home.xhtml";
        } else {
            this.isAuthenticated = false;
            this.user = null;
            this.username = "";
            this.messageModel.cleanOutMessages();
            this.messageModel.addMessage("Username is already registered!");
            return "register.xhtml";
        }       
    } 
}
