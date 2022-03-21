package com.finalProyect.CynthiaLabrador.follow.dto;

import com.finalProyect.CynthiaLabrador.follow.model.PeticionSeguimiento;
import com.finalProyect.CynthiaLabrador.users.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PeticionDtoConverter {
    public GetPeticionDto PeticionToGetPeticionDto(PeticionSeguimiento p){

        return GetPeticionDto.builder()
                .id(p.getId())
                .texto(p.getTexto())
                .build();
    }

    public PeticionSeguimiento createPeticionDtoToPeticion(CreatePeticionDto createPeticionDto, UserEntity user2){

        return PeticionSeguimiento.builder()
                .texto(createPeticionDto.getTexto())
                .destinatario(user2)
                .build();

    }
}