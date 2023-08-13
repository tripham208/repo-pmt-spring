package vn.id.pmt.spring.validation.impl;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.ObjectUtils;


import lombok.extern.log4j.Log4j2;
import vn.id.pmt.spring.validation.Required;


/**
 * Validate if a field has data or not.
 */
@Log4j2
public class RequiredValidator implements ConstraintValidator<Required, Object> {


    /**
     * @param constraintAnnotation Required
     */
    @Override
    public void initialize(Required arg0) {
    }


    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        boolean isValid = false;
        try {
            isValid = ObjectUtils.isNotEmpty(obj);
            System.out.println("validating");
        } catch (Exception ex) {
            log.error("Error when validating the specified object");
        }
        return isValid;
    }

}
