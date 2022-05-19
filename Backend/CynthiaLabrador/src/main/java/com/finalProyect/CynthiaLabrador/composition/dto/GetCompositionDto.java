package com.finalProyect.CynthiaLabrador.composition.dto;

import com.finalProyect.CynthiaLabrador.champions.dto.GetChampionDto;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class GetCompositionDto {
    private UUID id;
    private String name;
    private String description;
    private String authorName;
    private String date;
    private List<GetChampionDto> champions;
    private List<String> votes;
}

