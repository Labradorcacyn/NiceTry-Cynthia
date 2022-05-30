package com.finalProyect.CynthiaLabrador.validation.validadores;

import com.finalProyect.CynthiaLabrador.champions.repository.ChampionsRepository;
import com.finalProyect.CynthiaLabrador.validation.anotaciones.ChampionUniqueName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ChampionUniqueNameValidator implements ConstraintValidator<ChampionUniqueName, String> {
    @Autowired
    private ChampionsRepository championsRepository;

    @Override
    public void initialize(ChampionUniqueName constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return StringUtils.hasText(s) && !championsRepository.existsByName(s);
    }
}
