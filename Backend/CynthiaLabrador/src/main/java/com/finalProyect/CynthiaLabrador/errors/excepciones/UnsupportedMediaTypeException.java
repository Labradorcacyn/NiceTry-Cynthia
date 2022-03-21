package com.finalProyect.CynthiaLabrador.errors.excepciones;

import java.util.List;

public class UnsupportedMediaTypeException extends FileNotFoundException{


    public UnsupportedMediaTypeException(List<String> lista, Class clase) {
        super(String.format("El archivo del tipo %s no es soportado, pruebe con estas extensiones: %s", clase.getName(), lista));
    }


}