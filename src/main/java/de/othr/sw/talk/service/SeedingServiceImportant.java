package de.othr.sw.talk.service;

import de.othr.sw.talk.entity.Category;
import de.othr.sw.talk.entity.Posting;
import de.othr.sw.talk.entity.User;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Alternative;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

/**
 * Only important testdata. There is one Users (not admin)
 * and some categories without postings.
 * @author Karoc
 */
@ApplicationScoped
//@importantdata // if qualifier should work
@Alternative
public class SeedingServiceImportant implements SeedingServiceIF {
   
    private static final List<Category> category = new ArrayList<>();
    private static final List<User> user = new ArrayList<>();
    private static final List<Posting> posting = new ArrayList<>();
    
    static {
        user.add(new User("John", "secret"));
        
        category.add(new Category("Movies"));
        category.add(new Category("Technology"));
        category.add(new Category("News"));
        category.add(new Category("Politics"));
    }
    
    @PersistenceContext
    private EntityManager em;
    

    
    @Transactional(TxType.REQUIRES_NEW)
    @Override
    public void generateTestdata() {
        User check = em.find(User.class, "John");
        if(check != null)
            return;
        
        user.forEach( u -> em.persist(u) );
        
        category.forEach( m -> em.persist(m) );
        
        posting.forEach( p -> em.persist(p) );
        
    }
    
}
