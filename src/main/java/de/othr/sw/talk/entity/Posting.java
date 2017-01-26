package de.othr.sw.talk.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="Posting")
public class Posting extends Content implements Serializable {

    private String link, title;
    
    @ManyToOne
    private Category category;
    
    public Posting() {
        super();
    }

    public Posting(String link, String title, Category cat, User user, String text) {
        super(user, text, new Date());
        this.link = link;
        this.title = title;
        this.category = cat;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
    
}
