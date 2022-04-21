package com.finalProyect.CynthiaLabrador.champions.dto;

import com.finalProyect.CynthiaLabrador.champions.model.Champion;
import org.springframework.stereotype.Component;

@Component
public class ChampionDtoConverter {

    public GetChampionDto ChampionToGetChampionDto(Champion champion){
        return GetChampionDto.builder()
                .id(champion.getId())
                .name(champion.getName())
                .avatar(champion.getAvatar())
                .cost(champion.getCost())
                .build();
    }

    public Champion ChampionDtoToGetChampion(CreateChampionDto createChampionDto){
        return Champion.builder()
                .name(createChampionDto.getName())
                .cost(createChampionDto.getCost())
                .build();
    }
}
