package de.othr.sw.talk.entity;

import java.io.Serializable;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;


@Embeddable @Access(AccessType.FIELD)
public class UserInformation implements Serializable {

    private String email;

    public UserInformation() {
    }

    public UserInformation(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    
}
