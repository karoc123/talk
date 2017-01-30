package de.othr.sw.talk.model;

import de.othr.sw.talk.entity.Category;
import de.othr.sw.talk.entity.Posting;
import de.othr.sw.talk.entity.User;
import de.othr.sw.talk.entity.Vote;
import de.othr.sw.talk.service.PostingService;
import de.othr.sw.talk.service.UserService;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@SessionScoped
public class PostingModel implements Serializable{
    
    @Inject
    private PostingService postingService;
    
    @Inject
    private UserService userService;
    
    @Inject
    private UserModel userModel;
 
    @Inject
    private LoginModel loginModel;
    
    private long id;
    private String link, text, title;
    private Date date;
    private User user;
    private Category category;

    private long numberOfPostings;
    private int postingPaginationStart;

    public long getNumberOfPostings() {
        return numberOfPostings;
    }

    public void setNumberOfPostings(long numberOfPostings) {
        this.numberOfPostings = numberOfPostings;
    }

    public int getPostingPaginationStart() {
        return postingPaginationStart;
    }

    public void setPostingPaginationStart(int postingPaginationStart) {
        if(postingPaginationStart < 0) this.postingPaginationStart = 0;
        if(postingPaginationStart < this.numberOfPostings) this.postingPaginationStart = postingPaginationStart;
    }
    
    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
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
    
    public String create(){
        Posting post = postingService.createPosting(new Posting(this.link, this.title, this.category, loginModel.getUser(), this.text));
        return "viewposting.xhtml?faces-redirect=true&includeViewParams=true&post=" + post.getId();
    }
    
    public List<Posting> allPostings() {
        return this.postingService.getAllPostings();
    }
 
    public List<Posting> allPostingsForPage() {
        this.numberOfPostings = this.postingService.getNumberOfPostingsForAll();
        return this.postingService.getAllPostings(this.postingPaginationStart, 10);
    }
        
    public List<Posting> allPostingsByCategory() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String cat = params.get("cat");
        System.out.println(cat);
        return this.postingService.getAllPostingsByCategory(cat);
    }
}