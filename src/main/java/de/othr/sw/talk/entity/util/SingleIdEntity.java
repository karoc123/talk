package de.othr.sw.talk.entity.util;

import java.util.Objects;
import javax.persistence.MappedSuperclass;

/**
 * This class is from the OTHBib, not from me
 */
@MappedSuperclass
public abstract class SingleIdEntity<PK extends Comparable> implements Comparable<SingleIdEntity> {
    public abstract PK getId();
    
    @Override
    public boolean equals(Object other) {
        if(other instanceof SingleIdEntity)
            return Objects.equals(this.getId(), ((SingleIdEntity)other).getId());
        else
            return false;
    }
    
    @Override
    public int hashCode() {
        return Objects.hashCode(this.getId());
    }
    
    @Override
    public int compareTo(SingleIdEntity other) {
        if(this.getId() != null && other.getId() != null)
            return this.getId().compareTo(other.getId());
        else
            return 0;
    }
}