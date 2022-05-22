package com.finalProyect.CynthiaLabrador.champions.dto;

import com.finalProyect.CynthiaLabrador.traits.dto.GetTraitsDto;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class GetChampionDto {

    private UUID id;
    private String name;
    private int cost;
    private List<GetTraitsDto> traits;
    private String avatar;
}
