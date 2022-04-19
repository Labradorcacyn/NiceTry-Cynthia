package com.finalProyect.CynthiaLabrador.validation.anotaciones;

import com.finalProyect.CynthiaLabrador.validation.validadores.UserUniqueNickValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserUniqueNickValidator.class)
@Documented
public @interface ChampionUniqueName {
    String message() default "El nommbre del campeón ya existe";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
