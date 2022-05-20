package com.finalProyect.CynthiaLabrador.champions.dto;

import com.finalProyect.CynthiaLabrador.validation.anotaciones.ChampionUniqueName;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class CreateChampionDto {

    @ChampionUniqueName(message = "The name is already in use")
    @NotNull(message = "The name is mandatory")
    private String name;

    @NotNull(message = "Introduce how much cost the champion")
    private int cost;

    @NotNull(message="Image is mandatory")
    private String avatar;

    @NotNull(message="Need to introduce the name of the champion")
    private List<Long> traitsUuid;
}
