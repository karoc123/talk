
package de.othr.sw.talk.model;

import de.othr.sw.talk.service.UserService;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@SessionScoped
public class LoginModel implements Serializable{
    @Inject
    private UserService userService;
    
    private boolean isAuthenticated = false;
    
    private String username, password, errors;

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
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
        if (this.userService.authenticate(this.username, this.password)){
            this.isAuthenticated = true;
            return "home.xhtml";
        } else {
            this.errors = "wrong username or password";
            return "login.xhtml";
        }
    }
}
