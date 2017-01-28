package de.othr.sw.talk.entity;

import de.othr.sw.talk.entity.util.GeneratedIdEntity;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="Advertisement")
public class Advertisement extends GeneratedIdEntity implements Serializable {

    private String link;
    
    @Lob
    @Column
    private String text;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    public Advertisement(String link, String text) {
        this.link = link;
        this.text = text;
        this.creationDate = new Date();
    }

    public Advertisement() {
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
    
    @Override
    public String toString(){
        return this.text;
    }
}
