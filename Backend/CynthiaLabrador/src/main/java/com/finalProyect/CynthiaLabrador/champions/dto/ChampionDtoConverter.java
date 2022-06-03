package com.finalProyect.CynthiaLabrador.champions.dto;

import com.finalProyect.CynthiaLabrador.champions.model.Champion;
import com.finalProyect.CynthiaLabrador.traits.dto.TraitsDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ChampionDtoConverter {

    @Autowired
    private TraitsDtoConverter traitsDtoConverter;

    public GetChampionDto championToGetChampionDto(Champion champion){
        return GetChampionDto.builder()
                .id(champion.getId())
                .name(champion.getName())
                .cost(champion.getCost())
                .description(champion.getDescription())
                .traits(traitsDtoConverter.traitsToGetTraitsDto(champion.getTraits()))
                .build();
    }

    public Champion championDtoToGetChampion(CreateChampionDto createChampionDto){
        return Champion.builder()
                .name(createChampionDto.getName())
                .cost(createChampionDto.getCost())
                .description(createChampionDto.getDescription())
                .build();
    }

    public List<GetChampionDto> championsToGetChampionDto(List<Champion> champions){
        return champions.stream()
                .map(champion -> championToGetChampionDto(champion))
                .collect(Collectors.toList());
    }
}
