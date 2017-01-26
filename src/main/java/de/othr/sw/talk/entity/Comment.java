package de.othr.sw.talk.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="Comment")
public class Comment extends Content implements Serializable {

    @ManyToOne(fetch = FetchType.EAGER)
    private Comment parent;
    
    @ManyToOne(fetch = FetchType.EAGER)
    private Posting posting;
    
    public Comment() {
        super();
    }

    public Comment(Comment parent, Posting posting, User user, String text) {
        super(user, text, new Date());
        this.parent = parent;

        this.posting = posting;
    }

    public Comment getParent() {
        return parent;
    }

    public void setParent(Comment parent) {
        this.parent = parent;
    }

    public Posting getPosting() {
        return posting;
    }

    public void setPosting(Posting posting) {
        this.posting = posting;
    }
    
    
}
