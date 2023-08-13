package vn.id.pmt.spring.validation;


import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import vn.id.pmt.spring.validation.impl.RequiredValidator;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = {RequiredValidator.class})
@Documented
public @interface Required {

    String message() default "Required parameter is missing.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
