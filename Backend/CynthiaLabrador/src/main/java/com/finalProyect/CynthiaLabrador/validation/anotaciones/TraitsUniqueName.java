package com.finalProyect.CynthiaLabrador.validation.anotaciones;

import com.finalProyect.CynthiaLabrador.validation.validadores.TraitsUniqueNickValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TraitsUniqueNickValidator.class)
@Documented
public @interface TraitsUniqueName {
    String message() default "This trait already exists";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
