package com.finalProyect.CynthiaLabrador.validation.validadores;

import com.finalProyect.CynthiaLabrador.traits.repository.TraitsRepository;
import com.finalProyect.CynthiaLabrador.validation.anotaciones.TraitsUniqueName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TraitsUniqueNickValidator implements ConstraintValidator<TraitsUniqueName, String> {

    @Autowired
    private TraitsRepository traitsRepository;

    @Override
    public void initialize(TraitsUniqueName constraint) {
        ConstraintValidator.super.initialize(constraint);
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        return StringUtils.hasText(name) && !traitsRepository.existsByName(name);
    }
}
