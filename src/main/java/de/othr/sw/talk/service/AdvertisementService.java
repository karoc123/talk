package de.othr.sw.talk.service;

import javax.enterprise.context.RequestScoped;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@WebService
@RequestScoped
public class AdvertisementService { 
    @PersistenceContext(unitName="TalkPU")
    private EntityManager em;
    
    @Transactional
    public String createAdvertisement(String link, String text){
        //dummy
        return "dummy link to the advertisement";
    }
}
