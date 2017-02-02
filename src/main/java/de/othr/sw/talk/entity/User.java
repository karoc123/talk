package de.othr.sw.talk.entity;

import de.othr.sw.talk.entity.util.EntityUtils;
import de.othr.sw.talk.entity.util.StringIdEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Users have a unique username, a hashed password (with the salt)
 * and can be deactive. If they have admin rights, the admin flag
 * is true. In the userinformation entity are things like the e-mail.
 * A User can have postings.
 */
@Entity
@Table(name="TalkUser")
public class User extends StringIdEntity implements Serializable {

    public static final String HASH_ALGORITHM = "SHA-512";
    
    @Embedded
    private UserInformation userInformation;
    
    private String salt;
    private String password;
    
    private boolean isAdmin;
    private boolean isActiv;
    
    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST})
    private List<Posting> postings;
    
    public User() {
        super();        
        this.postings = new ArrayList<>();
        this.isActiv = true;
    }
    
    public User(String userId, String password){
        super(userId);
        this.postings = new ArrayList<>();
        this.salt = EntityUtils.createRandomString(4);
        this.isActiv = true;
        try {
            this.password = EntityUtils.hashPassword(password, this.salt, HASH_ALGORITHM);
        } catch (EntityUtils.EntityUtilException ex) {
            throw new RuntimeException("password can not be hashed", ex);
        }
        this.userInformation = new UserInformation();
    }

    public boolean isIsActiv() {
        return isActiv;
    }

    public void setIsActiv(boolean isActiv) {
        this.isActiv = isActiv;
    }

    public boolean isIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public UserInformation getUserInformation() {
        return userInformation;
    }

    public void setUserInformation(UserInformation userInformation) {
        this.userInformation = userInformation;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getUserId() {
        return super.getId();
    }


    public String getPassword() {
        return password;
    }
    
    public String getShortPassword(){
        return this.password.substring(0, Math.min(30, this.password.length())).concat("...");
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
