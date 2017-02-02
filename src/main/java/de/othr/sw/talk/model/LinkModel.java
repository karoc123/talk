package de.othr.sw.talk.model;

import de.othr.sw.talk.entity.Posting;
import de.othr.sw.talk.service.PostingService;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Users can export there links or upvoted links.
 */
@Named
@RequestScoped
public class LinkModel implements Serializable{
    
    @Inject
    private PostingService postingService;
    
    @Inject
    private LoginModel loginModel;

    public LinkModel() {
    }

    public List<Posting> getAllPostings() {
        return this.postingService.getAllPostings(loginModel.getUser());
    }
    
    public List<Posting> getAllUpvotedPostings() {
        return this.postingService.getAllUpvotedPostings(loginModel.getUser());
    }
}