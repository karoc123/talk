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

@ApplicationScoped
//@testdata // if qualifier should work
@Alternative	
public class SeedingServiceTestdata implements SeedingServiceIF {
   
    private static final List<Category> category = new ArrayList<>();
    private static final List<User> user = new ArrayList<>();
    private static final List<Posting> posting = new ArrayList<>();
    
    static {
        user.add(new User("John", "secret"));
        user.add(new User("Max", "geheim"));
        user.add(new User("Admin", "admin"));
        user.get(2).setIsAdmin(true);
        
        category.add(new Category("Movies"));
        category.add(new Category("Technology"));
        category.add(new Category("News"));
        category.add(new Category("Politics"));
        
        posting.add(new Posting("http://www.google.de", "Google search1", category.get(0), user.get(0), "Text about this link"));
        posting.add(new Posting("http://www.google.de", "Google search2", category.get(0), user.get(0), "Text about this link"));
        posting.add(new Posting("http://www.google.de", "Google search3", category.get(0), user.get(0), "Text about this link"));
        posting.add(new Posting("http://www.google.de", "Google search4", category.get(0), user.get(0), "Text about this link"));
        posting.add(new Posting("http://www.google.de", "Google search5", category.get(0), user.get(0), "Text about this link"));
        posting.add(new Posting("http://www.google.de", "Google search6", category.get(0), user.get(0), "Text about this link"));
        posting.add(new Posting("http://www.google.de", "Google search7", category.get(0), user.get(0), "Text about this link"));
        posting.add(new Posting("http://www.google.de", "Google search8", category.get(0), user.get(0), "Text about this link"));
        posting.add(new Posting("http://www.google.de", "Google search9", category.get(0), user.get(0), "Text about this link"));
        posting.add(new Posting("http://www.google.de", "Google search10", category.get(0), user.get(0), "Text about this link"));
        posting.add(new Posting("http://www.google.de", "Google search11", category.get(0), user.get(0), "Text about this link"));
        posting.add(new Posting("http://www.google.de", "Google search12", category.get(0), user.get(0), "Text about this link"));
        posting.add(new Posting("http://www.google.de", "Google search13", category.get(0), user.get(0), "Text about this link"));
        posting.add(new Posting("http://www.google.de", "Google search14", category.get(0), user.get(0), "Text about this link"));
        posting.add(new Posting("http://www.google.de", "Google search15", category.get(0), user.get(0), "Text about this link"));
        posting.add(new Posting("http://www.google.de", "Google search16", category.get(0), user.get(0), "Text about this link"));
        posting.add(new Posting("http://www.google.de", "Google search17", category.get(0), user.get(0), "Text about this link"));
        posting.add(new Posting("http://www.google.de", "Google search18", category.get(0), user.get(0), "Text about this link"));
        posting.add(new Posting("http://www.google.de", "Google search19", category.get(0), user.get(0), "Text about this link"));
        posting.add(new Posting("http://www.google.de", "Google search20", category.get(0), user.get(0), "Text about this link"));
        posting.add(new Posting("http://www.google.de", "Google search21", category.get(0), user.get(0), "Text about this link"));
        posting.add(new Posting("http://www.google.de", "Google search22", category.get(0), user.get(0), "Text about this link"));
        posting.add(new Posting("http://www.google.de", "Google search23", category.get(0), user.get(0), "Text about this link"));
        posting.add(new Posting("http://www.google.de", "Google search24", category.get(0), user.get(0), "Text about this link"));
        posting.add(new Posting("http://www.google.de", "Google search25", category.get(0), user.get(0), "Text about this link"));
        posting.add(new Posting("http://www.google.de", "Google search26", category.get(0), user.get(0), "Text about this link"));
        posting.add(new Posting("http://www.google.de", "Google search27", category.get(0), user.get(0), "Text about this link"));
        posting.add(new Posting("http://www.google.de", "Google search28", category.get(0), user.get(0), "Text about this link"));
        posting.add(new Posting("http://www.google.de", "Google search29", category.get(0), user.get(0), "Text about this link"));
        posting.add(new Posting("http://www.google.de", "Google search30", category.get(0), user.get(0), "Text about this link"));
        posting.add(new Posting("http://www.google.de", "Google search31", category.get(0), user.get(0), "Text about this link"));
        posting.add(new Posting("http://www.google.de", "Google search32", category.get(0), user.get(0), "Text about this link"));
        posting.add(new Posting("http://www.google.de", "Google search33", category.get(0), user.get(0), "Text about this link"));
        posting.add(new Posting("http://www.google.de", "Google search34", category.get(0), user.get(0), "Text about this link"));
        posting.add(new Posting("http://www.google.de", "Google search35", category.get(0), user.get(0), "Text about this link"));
        posting.add(new Posting("http://www.google.de", "Google search36", category.get(0), user.get(0), "Text about this link"));    
        
    }
    
    @PersistenceContext
    private EntityManager em;
    

    
    @Transactional(TxType.REQUIRES_NEW)
    @Override
    public void generateTestdata() {
        User check = em.find(User.class, "Max");
        if(check != null)
            return;
        
        user.forEach( u -> em.persist(u) );
        
        category.forEach( m -> em.persist(m) );
        
        posting.forEach( p -> em.persist(p) );
        
    }
    
}
