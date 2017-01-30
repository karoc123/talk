package de.othr.sw.talk.service;

import de.othr.stefan_soelch.service.PubRemoteServiceService;
import de.othr.sw.talk.entity.Advertisement;
import de.othr.sw.talk.entity.User;
import de.othr.stefan_soelch.service.*;
import java.util.List;
import java.util.Random;
import javax.enterprise.context.RequestScoped;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.xml.ws.WebServiceRef;

/**
 * This service is for super simple advertisements
 * @author tobias zink
 */
@WebService
@RequestScoped
public class AdvertisementService { 

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/im-lamport_8080/pubProject_soelch/PubRemoteService.wsdl")
    private PubRemoteServiceService service;

    @PersistenceContext(unitName="TalkPU")
    private EntityManager em;
    
    /**
     * Create and persist a new advertisement
     * @param link A hyperlink to some other website
     * @param text Text of the advertisement
     * @return link to add management
     */
    @Transactional
    public String createAdvertisement(String link, String text){
        
        StringBuilder url = new StringBuilder();

        if (!link.startsWith("http://", 0)) {
            url.append("http://");
        }
        url.append(link);
        
        Advertisement newAd = new Advertisement(url.toString(), text);   
        
        em.persist(newAd);
        return "ZinkTalk/faces/views/managead.xhtml";
    }

    /**
     * A list of all advertisements
     * @return list of advertisements
     */
    public List<Advertisement> getAllAdvertisements() {
        TypedQuery<Advertisement> query = em.createQuery("SELECT u FROM Advertisement AS u ORDER BY u.creationDate DESC", Advertisement.class);
        return query.getResultList();
    }

    /**
     * 
     */
    @Transactional
    public void createRemoteAdvertisement() {
        
        try { // Call Web Service Operation
            PubRemoteService port = service.getPubRemoteServicePort();
            // TODO process result here
            java.util.List<Reservation> result;
            result = port.findAllReservations();
            System.out.println("Result = "+result);
            if(!result.isEmpty()){
                String link = result.get(0).getRestaurant().getWebAddress().getUrl();
                String text = "Name: "
                        .concat(result.get(0).getRestaurant().getName())
                        .concat(" - Seats: ")
                        .concat(String.valueOf(result.get(0).getRestaurant().getSeats()))
                        .concat(" - Seats available: ")
                        .concat(String.valueOf(result.get(0).getRestaurant().getSeats()-result.get(0).getRestaurant().getOccupiedSeats()));
                this.createAdvertisement(link, text);               
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
            System.out.println("Remote Service maybe not available. Check error message!");
            System.out.println(ex.toString());
        }

    }

    /**
     * A random advertisment from the database
     * @return 
     */
    public Advertisement getRandomAdvertisement() {
        Random generator = new Random(); 

        TypedQuery<Advertisement> query = em.createQuery("SELECT u FROM Advertisement AS u ORDER BY u.creationDate DESC", Advertisement.class);
        List<Advertisement> ad = query.getResultList();
        if(ad.isEmpty()) return null;
        return ad.get(generator.nextInt(ad.size()));
    }
    
}
