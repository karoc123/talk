package de.othr.sw.talk.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 * User generated content: Postings and Comments
 */
@MappedSuperclass
public class Content implements Serializable {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
    
    @OneToMany(fetch = FetchType.EAGER)
    @ElementCollection
    private Set<Vote> vote;
    
    @Lob
    @Column
    private String text;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    
    private int voting;
    
    public Content() {
        
    }

    public Content(User user, String text, Date datum) {
        this.user = user;
        this.text = text;
        this.creationDate = datum;
        this.voting = 0;
    }

    public int getVoting() {
        return voting;
    }

    public void setVoting(int voting) {
        this.voting = voting;
    }

    public Set<Vote> getVote() {
        return vote;
    }

    public void setVote(Set<Vote> vote) {
        this.vote = vote;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDatum() {
        return creationDate;
    }

    public void setDatum(Date datum) {
        this.creationDate = datum;
    }
    
    
}
