package com.finalProyect.CynthiaLabrador.errors.excepciones;

public class SingleEntityNotFoundException2 extends EntityNotFoundException{

    public SingleEntityNotFoundException2(String nick, Class clase) {
        super(String.format("No se puede encontrar una entidad del tipo %s con nick: %s", clase.getName(), nick));
    }
}