package de.othr.sw.talk.entity.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * This validator is written with snippets from stackoverflow
 */
@FacesValidator("de.othr.sw.talk.entity.util.EmailValidator")
public class EmailValidator implements Validator {

 
    private final static String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
     
    private final static Pattern EMAIL_COMPILED_PATTERN = Pattern.compile(EMAIL_PATTERN);
     
    /**
     * Validate method.
     */
    public void validate(FacesContext fc, UIComponent c, Object o) throws ValidatorException {
        // No value is not ok
        if (o == null || "".equals((String)o)) {
            FacesMessage msg = new FacesMessage("No email value!", "Email Validation Error");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
         
        // The email matcher
        Matcher matcher = EMAIL_COMPILED_PATTERN.matcher((String)o);
         
        if (!matcher.matches()) {   // Email doesn't match
            FacesMessage msg = new FacesMessage("Invalid email value!", "Email Validation Error");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
         
    }
}
