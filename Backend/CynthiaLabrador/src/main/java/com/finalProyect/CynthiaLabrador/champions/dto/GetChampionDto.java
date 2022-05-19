package com.finalProyect.CynthiaLabrador.champions.dto;

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
    private List<String> traits;
    private String avatar;
}
