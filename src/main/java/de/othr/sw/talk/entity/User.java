package de.othr.sw.talk.entity;

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

    private String password;
    
    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST})
    private List<Posting> postings = new ArrayList<Posting>();
    
    public User() {
        super();        
    }
    
    public User(String userId, String password){
        super(userId);
        this.password = password;
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
