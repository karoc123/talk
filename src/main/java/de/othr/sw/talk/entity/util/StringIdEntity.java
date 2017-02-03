package de.othr.sw.talk.entity.util;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * This class is from the OTHBib, not from me
 */
@MappedSuperclass
public class StringIdEntity extends SingleIdEntity<String> {

    @Id
    private String id;
    
    protected StringIdEntity() { }
    
    protected StringIdEntity(String id) {
        this.id = id;
    }
    
    @Override
    public String getId() {
        return this.id;
    }
    
}
