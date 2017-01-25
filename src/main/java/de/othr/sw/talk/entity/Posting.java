package de.othr.sw.talk.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Posting")
public class Posting extends Content implements Serializable {

    private String link;
    
    public Posting() {
        super();
    }

    public Posting(String link, User user, String text, Date datum) {
        super(user, text, datum);
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
