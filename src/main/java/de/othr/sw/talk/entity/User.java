package de.othr.sw.talk.entity;

import de.othr.sw.talk.entity.util.EntityUtils;
import de.othr.sw.talk.entity.util.StringIdEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="TalkUser")
public class User extends StringIdEntity implements Serializable {

    public static final String HASH_ALGORITHM = "SHA-512";
    
    private String salt;
    private String password;
    
    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST})
    private List<Posting> postings;
    
    public User() {
        super();        
        this.postings = new ArrayList<Posting>();
    }
    
    public User(String userId, String password){
        super(userId);
        this.postings = new ArrayList<>();
        this.salt = EntityUtils.createRandomString(4);
        
        try {
            this.password = EntityUtils.hashPassword(password, this.salt, HASH_ALGORITHM);
        } catch (EntityUtils.EntityUtilException ex) {
            throw new RuntimeException("password can not be hashed", ex);
        }
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

    public void setPassword(String password) {
        this.password = password;
    }
    
}
