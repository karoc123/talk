package de.othr.sw.talk.entity.util;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


@MappedSuperclass
public class GeneratedIdEntity extends SingleIdEntity<Long> {

    @Id
    @GeneratedValue
    protected Long id;
    
    @Override
    public Long getId() {
        return this.id;
    }
    
}
