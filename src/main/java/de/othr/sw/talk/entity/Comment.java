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

/**
 * Postings can be commented. Comments can also be commented.
 */
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

    /**
     * Adds give comment to the child list of the comment
     * @param com 
     */
    public void addCommentToChildList(Comment com){
        this.childComments.add(com);
    }
    
    /**
     * Create a comment
     * @param parent parent comment if there is one
     * @param posting posting for the comment
     * @param user user who made the comment
     * @param text text of the comment
     */
    public Comment(Comment parent, Posting posting, User user, String text) {
        super(user, text, new Date());
        this.parent = parent;
        this.childComments = new ArrayList<>();
    }

    /**
     * True if the comment has child comments
     * @return 
     */
    public boolean hasChild(){
        return !childComments.isEmpty();
    }
    
    public Comment getParent() {
        return parent;
    }

    public void setParent(Comment parent) {
        this.parent = parent;
    }

    /**
     * To sort comments by date
     * @param c2
     * @return 
     */
    public int compareTo(Comment c2) {
        return c2.getDatum().compareTo(this.getDatum());
    }
}
