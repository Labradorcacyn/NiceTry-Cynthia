package com.finalProyect.CynthiaLabrador.champions.dto;

import lombok.*;

import java.util.UUID;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class GetChampionDto {

    private UUID id;
    private String name;
    private int cost;
    private String password;
    //private List<>traits;
    private String avatar;
}
