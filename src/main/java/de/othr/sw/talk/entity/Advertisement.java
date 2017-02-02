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

/**
 * Advertisements consist of a link to a website and some text.
 * They can be created by other applications.
 */
@Entity
@Table(name="Advertisement")
public class Advertisement extends GeneratedIdEntity implements Serializable {

    private String link;
    
    @Lob
    @Column
    private String text;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    /**
     * 
     * @param link
     * @param text 
     */
    public Advertisement(String link, String text) {
        this.link = link;
        this.text = text;
        this.creationDate = new Date();
    }

    /**
     * 
     */
    public Advertisement() {
    }

    /**
     * 
     * @return 
     */
    public String getLink() {
        return link;
    }

    /**
     * 
     * @param link 
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * 
     * @return 
     */
    public String getText() {
        return text;
    }

    /**
     * 
     * @param text 
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * 
     * @return 
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * 
     * @param creationDate 
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public String toString(){
        return this.text;
    }
}
