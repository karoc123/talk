package de.othr.sw.talk.entity.util;

import java.net.URI;
import java.net.URISyntaxException;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("de.othr.sw.talk.entity.util.UrlValidator")
public class UrlValidator implements Validator {

    @Override
    public void validate(FacesContext facesContext,
            UIComponent component, Object value)
            throws ValidatorException {

        System.out.println("validation");
        
        StringBuilder url = new StringBuilder();
        String urlValue = value.toString();

        if (!urlValue.startsWith("http://", 0)) {
            url.append("http://");
        }
        url.append(urlValue);

        try {
            new URI(url.toString());
        } catch (URISyntaxException e) {
            FacesMessage msg
                    = new FacesMessage("URL validation failed", "Invalid URL format");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }
}
