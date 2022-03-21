package com.finalProyect.CynthiaLabrador.follow.services;

import com.finalProyect.CynthiaLabrador.errors.excepciones.ListEntityNotFoundException;
import com.finalProyect.CynthiaLabrador.follow.model.PeticionSeguimiento;
import com.finalProyect.CynthiaLabrador.follow.repository.PeticionSeguimientoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class PeticionService {

    private final PeticionSeguimientoRepository peticionSeguimientoRepository;

    public List<PeticionSeguimiento> findAll (){

        List<PeticionSeguimiento> data = peticionSeguimientoRepository.findAll();

        if (data.isEmpty()){
            throw new ListEntityNotFoundException(PeticionSeguimiento.class);
        }else{
            return data;
        }
    }
}
