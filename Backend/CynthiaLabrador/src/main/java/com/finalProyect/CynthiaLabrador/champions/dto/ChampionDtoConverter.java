package com.finalProyect.CynthiaLabrador.champions.dto;

import com.finalProyect.CynthiaLabrador.champions.model.Champion;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ChampionDtoConverter {

    public GetChampionDto ChampionToGetChampionDto(Champion champion){
        return GetChampionDto.builder()
                .id(champion.getId())
                .name(champion.getName())
                .avatar(champion.getAvatar())
                .cost(champion.getCost())
                .traits(champion.getTraits() != null ? champion.getTraits().stream().map(trait -> trait.getName()).collect(Collectors.toList()) : null)
                .build();
    }

    public Champion ChampionDtoToGetChampion(CreateChampionDto createChampionDto){
        return Champion.builder()
                .name(createChampionDto.getName())
                .avatar(createChampionDto.getAvatar())
                .cost(createChampionDto.getCost())
                .build();
    }

    public List<GetChampionDto> ChampionsToGetChampionDto(List<Champion> champions){
        return champions.stream()
                .map(champion -> ChampionToGetChampionDto(champion))
                .collect(Collectors.toList());
    }
}
