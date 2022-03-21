package com.finalProyect.CynthiaLabrador.validation.validadores;

import com.finalProyect.CynthiaLabrador.users.repository.UserEntityRepository;
import com.finalProyect.CynthiaLabrador.validation.anotaciones.UserUniqueNick;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserUniqueNickValidator implements ConstraintValidator<UserUniqueNick, String> {

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Override
    public void initialize(UserUniqueNick constraintAnnotation){}
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return StringUtils.hasText(s) && !userEntityRepository.existsByNick(s);
    }
}