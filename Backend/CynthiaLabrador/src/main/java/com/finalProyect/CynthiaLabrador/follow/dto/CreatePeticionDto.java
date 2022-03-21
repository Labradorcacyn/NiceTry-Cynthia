package com.finalProyect.CynthiaLabrador.follow.dto;

import lombok.*;

import javax.persistence.Lob;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class CreatePeticionDto {

    @Lob
    private String texto;
}