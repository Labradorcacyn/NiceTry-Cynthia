package com.finalProyect.CynthiaLabrador.errors.excepciones;

public class UserEntityException extends EntityNotFoundException{

    public UserEntityException(String message) {
        super(message);
    }
}