package de.othr.sw.talk.entity.util;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


@MappedSuperclass
public class RandomIdEntity extends SingleIdEntity<String> {

    @Id
    protected String id;
    
    protected RandomIdEntity() {
        this.id = EntityUtils.createRandomString(4); 
        // alternativ: this.id = EntityUtils.createRandomUUID();
    }
    
            
    @Override
    public String getId() {
        return this.id;
    }
    
}
