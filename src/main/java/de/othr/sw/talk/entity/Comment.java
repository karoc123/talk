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
    
    @OneToMany(fetch = FetchType.EAGER)
    private List<Comment> childComments;
    
    public Comment() {
        super();
    }

    public void addCommentToChildList(Comment com){
        this.childComments.add(com);
    }
    public Comment(Comment parent, Posting posting, User user, String text) {
        super(user, text, new Date());
        this.parent = parent;
        this.childComments = new ArrayList<>();
    }

    public boolean hasChild(){
        return !childComments.isEmpty();
    }
    
    public Comment getParent() {
        return parent;
    }

    public void setParent(Comment parent) {
        this.parent = parent;
    }

    public int compareTo(Comment c2) {
        return c2.getDatum().compareTo(this.getDatum());
    }
}
