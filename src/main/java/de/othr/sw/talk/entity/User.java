package de.othr.sw.talk.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author karoc
 */
@Entity
@Table(name="TalkUser")
public class User implements Serializable {
    @Id
    private String userId;
    private String password;
    
    public User() {
        
    }
    
    public User(String userId, String password){
        this.userId = userId;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
