
package de.othr.sw.talk.model;

import de.othr.sw.talk.entity.Advertisement;
import de.othr.sw.talk.entity.Category;
import de.othr.sw.talk.entity.converter.CategoryConverterImpl;
import de.othr.sw.talk.service.AdvertisementService;
import de.othr.sw.talk.service.PostingService;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class AdvertisementModel implements Serializable {
    
    @Inject
    private AdvertisementService advertisementService;
    
    private String text, link;
    private Date date;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    public void create(){
        this.advertisementService.createAdvertisement(this.link, this.text);
        this.link = "";
        this.text = "";
    }
    
    public void createRemoteAd(){
        this.advertisementService.createRemoteAdvertisement();
    }

    public List<Advertisement> getAllAdvertisements() {
        return this.advertisementService.getAllAdvertisements();
    }
}
