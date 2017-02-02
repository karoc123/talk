
package de.othr.sw.talk.model;

import de.othr.sw.talk.entity.User;
import de.othr.sw.talk.service.SeedingServiceTestdata;
import de.othr.sw.talk.service.SeedingServiceIF;
import de.othr.sw.talk.service.UserService;
import de.othr.sw.talk.service.importantdata;
import de.othr.sw.talk.service.testdata;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * This model holds the "authentication" of the user.
 * If login was successful, isAuthenticated is true.
 */
@Named
@SessionScoped
public class LoginModel implements Serializable{
    @Inject
    private UserService userService;

    //@Inject @testdata
    //@Inject @importantdata
    @Inject
    private SeedingServiceIF seedingService;
    
    @Inject
    private MessageModel messageModel;
    
    private boolean isAuthenticated = false;
    private boolean isAdmin = false;
    
    private String username, password, errors, email, newPassword;

    private User user;

    public LoginModel() {
    }

    @PostConstruct
    public void testdata() {
        seedingService.generateTestdata();
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    /**
     * if true, the user should have admin rights
     * @return 
     */
    public boolean isIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
    
    /**
     * If password OR email are wrong, there will be a error message
     * @return 
     */
    public String authenticate(){
        user = this.userService.authenticate(this.username, this.password);
        if (user != null){
            this.isAuthenticated = true;
            if(user.getUserInformation() != null) this.email = user.getUserInformation().getEmail();
            this.messageModel.cleanOutMessages();
            this.isAdmin = user.isIsAdmin();
            return "home.xhtml";
        } else {
            this.isAuthenticated = false;
            this.messageModel.cleanOutMessages();
            this.messageModel.addMessage("Username or password wrong!");
            return "login.xhtml";
        }
    }
    
    public void changeEmail() {
        this.userService.changeEmail(this.email, this.user);
        this.messageModel.cleanOutMessages();
        this.messageModel.addMessage("Email changed!");
    }
 
    public void changePassword() {
        if(this.userService.changePassword(this.password, this.newPassword, this.user)){
            this.messageModel.cleanOutMessages();
            this.messageModel.addMessage("Password changed!");
        } else {
            this.messageModel.cleanOutMessages();
            this.messageModel.addMessage("Error, password wrong?");
        }
    }
    public String logout(){
        this.isAuthenticated = false;
        this.user = null;
        this.username = "";
        this.password = "";
        this.email = "";
        this.isAdmin = false;
        this.messageModel.cleanOutMessages();
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
