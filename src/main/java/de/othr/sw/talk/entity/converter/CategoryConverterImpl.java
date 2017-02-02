
package de.othr.sw.talk.entity.converter;

import de.othr.sw.talk.entity.Category;
import de.othr.sw.talk.service.PostingService;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

/**
 * Converts categories for dropdown
 */
@FacesConverter("de.othr.sw.talk.entity.converter.CategoryConverterImpl")
@RequestScoped
public class CategoryConverterImpl implements Converter {

    @Inject
    private PostingService postingService;
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null){
            return "";
        }
        Category cat = postingService.getCategoryByString(value);
        if (cat == null){
            return "";
        }
        return cat;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if  (value == null){
            return null;
        }
        if (!value.getClass().equals(Category.class)){
            return null;
        }
        return ((Category)value).getName();
    }
    
}
