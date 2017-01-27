package de.othr.sw.talk.model;

import de.othr.sw.talk.entity.Comment;
import de.othr.sw.talk.entity.Posting;
import de.othr.sw.talk.entity.User;
import de.othr.sw.talk.service.PostingService;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@SessionScoped
public class CommentModel implements Serializable{
    
    @Inject
    private PostingService postingService;
    
    @Inject
    private UserModel userModel;
    
    @Inject
    private LoginModel loginModel; 
    
    private String text;
    private Date date;
    private User user;
    private long postingId, parentCommentId;
    private Posting posting;
    private Comment parent;
    private List<Comment> comments;
    
    public void loadData() {
        this.posting = postingService.getPostingById(this.postingId);
        this.user = loginModel.getUser();
    }
    
    public void create(){
        this.parent = postingService.getCommentById(this.parentCommentId);
        Comment com = postingService.createComment(new Comment(this.parent, this.posting, this.user, this.text));
        this.text = "";
    }

    public List<Comment> allCommentsForPosting() {
        return this.postingService.getAllCommentsByPostingId(postingId);
    }

    public List<Comment> allRootCommentsForPosting() {
        return this.postingService.getallRootCommentsForPosting(postingId);
    }
    
    public List<Comment> allChildComments() {
        return this.postingService.allChildCommentsForPosting(postingId);
    }
    
    public long getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(long parentCommentId) {
        this.parentCommentId = parentCommentId;
    }
    
    public long getPostingId() {
        return postingId;
    }

    public void setPostingId(long postingId) {
        this.postingId = postingId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}