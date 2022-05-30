package com.finalProyect.CynthiaLabrador.validation.anotaciones;

import com.finalProyect.CynthiaLabrador.validation.validadores.UserUniqueNickValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserUniqueNickValidator.class)
@Documented
public @interface UserUniqueNick {

    String message() default "This nick is already in use";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
