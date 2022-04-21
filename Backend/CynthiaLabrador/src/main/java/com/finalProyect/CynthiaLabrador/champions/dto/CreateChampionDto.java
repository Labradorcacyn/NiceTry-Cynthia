package com.finalProyect.CynthiaLabrador.champions.dto;

import com.finalProyect.CynthiaLabrador.validation.anotaciones.ChampionUniqueName;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class CreateChampionDto {

    @ChampionUniqueName(message = "El nombre del campeón ya está en uso")
    private String name;

    @NotNull
    private int cost;

    private String avatar;
}
