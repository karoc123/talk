package de.othr.sw.talk.model;

import de.othr.sw.talk.entity.Category;
import de.othr.sw.talk.entity.Posting;
import de.othr.sw.talk.entity.User;
import de.othr.sw.talk.entity.Vote;
import de.othr.sw.talk.service.PostingService;
import java.io.Serializable;
import java.util.Date;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Model for a single posting
 */
@Named
@SessionScoped
public class PostingDetailModel implements Serializable{
    
    @Inject
    private PostingService postingService;
    
    @Inject
    private UserModel userModel;
    
    @Inject
    private LoginModel loginModel;
    
    private String link, text, title;
    private Date date;
    private User user;
    private Category category;
    private long id;
    private Posting post;
    private int voting;
    private boolean voted;

    public void loadData() {
        post = postingService.getPostingById(this.id);
        this.category = post.getCategory();
        this.link = post.getLink();
        this.date = post.getDatum();
        this.text = post.getText();
        this.title = post.getTitle();
        this.user = post.getUser();
        this.voting = post.getVoting();
        this.voted = postingService.checkIfUserVoted(post, loginModel.getUser());
    }

    public boolean isVoted() {
        return voted;
    }

    public boolean getVoted() {
        return voted;
    }
    public void setVoted(boolean voted) {
        this.voted = voted;
    }
 
    public void voteUp(){
        if(loginModel.isIsAuthenticated()){
           post = this.postingService.getPostingById(this.id);
           this.postingService.updateVotePosting(post, loginModel.getUser(), true);
            this.voted = true;
        }
    }
 
    public void voteDown(){
        if(loginModel.isIsAuthenticated()){
           post = this.postingService.getPostingById(this.id);
           this.postingService.updateVotePosting(post, loginModel.getUser(), false);
            this.voted = true;
        }
    }
    
    public String delete(){
        this.post = this.postingService.getPostingById(this.id);
        if(this.postingService.delete(this.post, loginModel.getUser())){
            return "home.xhtml";
        }
        
        return null;
    }
    
    public int getVoting() {
        return voting;
    }

    public void setVoting(int voting) {
        this.voting = voting;
    }
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}