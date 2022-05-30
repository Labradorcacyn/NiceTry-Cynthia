package com.finalProyect.CynthiaLabrador.errors.excepciones;

import java.util.List;

public class UnsupportedMediaTypeException extends FileNotFoundException{


    public UnsupportedMediaTypeException(List<String> lista, Class clase) {
        super(String.format("El archivo del tipo %s no es soportado, pruebe con estas extensiones: %s", clase.getName(), lista));
    }


    public UnsupportedMediaTypeException(String archivo_vacio) {
        super(String.format("El archivo %s no es soportado", archivo_vacio));
    }
}