
package de.othr.sw.talk.model;

import de.othr.sw.talk.entity.Category;
import de.othr.sw.talk.entity.converter.CategoryConverterImpl;
import de.othr.sw.talk.service.PostingService;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Every posting has a category (but only one). Postings can be filterd
 * by the category.
 */
@Named
@RequestScoped
public class CategoryModel implements Serializable {
    
    @Inject
    private PostingService postingService;
    
    @Inject
    private MessageModel mesasgeModel;
    
    @Inject
    private CategoryConverterImpl injectedCategoryConverter;
    
    private String name;
    private List<Category> allCategorys;

    public void create(){
        if(postingService.createCategory(new Category(this.name)) == null)
        {
            mesasgeModel.cleanOutMessages();
            mesasgeModel.addMessage("Category already there!");
        } else {
            mesasgeModel.cleanOutMessages();
        } 
        this.name = null;
    }

    public List<Category> getAllCategorys() {
        this.allCategorys = this.postingService.getAllCategorys();
        return allCategorys;
    }

    public void setAllCategorys(List<Category> allCategorys) {
        this.allCategorys = allCategorys;
    }
    
    /**
     * call service to delete category
     * @param toDelete 
     */
    public void delete(Category toDelete){
        this.postingService.deleteCategory(toDelete);
    }

    public CategoryConverterImpl getInjectedCategoryConverter() {
        return injectedCategoryConverter;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public boolean equals(Object other) {
        if (this == other) 
            return true; 
        if (!super.equals(other)) 
            return false;
        if (!(this.name.equals(((CategoryModel)other).name))) 
            return false; 
       return true; 
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.name);
        return hash;
    }
}
