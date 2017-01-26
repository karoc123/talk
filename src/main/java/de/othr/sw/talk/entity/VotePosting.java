package de.othr.sw.talk.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="VotePosting")
public class VotePosting implements Serializable {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
        
    private boolean vote;
    
    @ManyToOne(fetch = FetchType.EAGER)
    private Posting posting;
    
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    public VotePosting() {
    }

    public VotePosting(boolean vote, Posting posting, User user) {
        this.vote = vote;
        this.posting = posting;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isVote() {
        return vote;
    }

    public void setVote(boolean vote) {
        this.vote = vote;
    }

    public Posting getPosting() {
        return posting;
    }

    public void setPosting(Posting posting) {
        this.posting = posting;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    
}
