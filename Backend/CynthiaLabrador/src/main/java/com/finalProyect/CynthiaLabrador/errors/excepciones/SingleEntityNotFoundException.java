package com.finalProyect.CynthiaLabrador.errors.excepciones;

public class SingleEntityNotFoundException extends EntityNotFoundException{

    public SingleEntityNotFoundException(String id, Class clase) {
        super(String.format("No se puede encontrar una entidad del tipo %s : %s", clase.getName(), id));
    }
}

