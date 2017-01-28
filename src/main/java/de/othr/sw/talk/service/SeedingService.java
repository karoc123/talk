package de.othr.sw.talk.service;

import de.othr.sw.talk.entity.Category;
import de.othr.sw.talk.entity.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

@RequestScoped
public class SeedingService {
   
    private static final List<Category> category = new ArrayList<>();
    private static final List<User> user = new ArrayList<>();
    
    static {
        user.add(new User("John", "secret"));
        user.add(new User("Max", "geheim"));
        user.add(new User("Admin", "admin"));
        
        category.add(new Category("Movies"));
        category.add(new Category("Technology"));
        category.add(new Category("News"));
        category.add(new Category("Politics"));
    }
    
    @PersistenceContext
    private EntityManager em;
    

    
    @Transactional(TxType.REQUIRES_NEW)
    public void generateTestdata() {
        User check = em.find(User.class, "Max");
        if(check != null)
            return;
        
        user.forEach( u -> em.persist(u) );
        
        category.forEach( m -> em.persist(m) );
        
    }
    
}
