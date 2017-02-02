package de.othr.sw.talk.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Every posting has one (and exactly one) category.
 * The postings can be filtered by category.
 * If a category gets deleted, all postings of the category
 * also get deleted.
 */
@Entity
@Table(name="Category")
public class Category implements Serializable {
    @Id
    private String name;
    
    public Category() {
        
    }

    public Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String toString(){
        return this.name;
    }
    
    @Override
    public boolean equals(Object other) {
        if (this == other) 
            return true; 
        if (!(this.name.equals(((Category)other).name))) 
            return false; 
       return true; 
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.name);
        return hash;
    }
}
