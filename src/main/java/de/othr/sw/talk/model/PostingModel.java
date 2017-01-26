package de.othr.sw.talk.model;

import de.othr.sw.talk.entity.Category;
import de.othr.sw.talk.entity.Posting;
import de.othr.sw.talk.entity.User;
import de.othr.sw.talk.service.PostingService;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class PostingModel implements Serializable{
    
    @Inject
    private PostingService postingService;
    
    @Inject
    private UserModel userModel;
    
    private String link, text, title;
    private Date date;
    private User user;
    private Category category;

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
        Posting post = postingService.createPosting(new Posting(this.link, this.title, this.category, userModel.getLastGenerated(), this.text));
        return "viewposting.xhtml?faces-redirect=true&includeViewParams=true&post=" + post.getId();
    }
    
    public List<Posting> allPostings() {
        return this.postingService.getAllPostings();
    }
    
    public List<Posting> allPostingsByCategory() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String cat = params.get("cat");
        System.out.println(cat);
        return this.postingService.getAllPostingsByCategory(cat);
    }
}