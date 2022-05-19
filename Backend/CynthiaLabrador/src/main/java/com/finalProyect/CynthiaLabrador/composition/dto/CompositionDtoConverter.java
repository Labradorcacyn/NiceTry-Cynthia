package com.finalProyect.CynthiaLabrador.composition.dto;

import com.finalProyect.CynthiaLabrador.champions.dto.ChampionDtoConverter;
import com.finalProyect.CynthiaLabrador.composition.model.Composition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CompositionDtoConverter {

    @Autowired
    private ChampionDtoConverter championDtoConverter;

    public GetCompositionDto CompositionToGetCompositionDto(Composition composition){
        return GetCompositionDto.builder()
                .id(composition.getId())
                .name(composition.getName())
                .description(composition.getDescription())
                .authorName(composition.getAuthor().getName())
                .date(composition.getCreateTime().toString())
                .votes(composition.getVotes())
                .champions(championDtoConverter.ChampionsToGetChampionDto(composition.getChampions()))
                .build();
    }

    public Composition CompositionDtoToGetComposition(CreateCompositionDto createCompositionDto){
        return Composition.builder()
                .name(createCompositionDto.getName())
                .description(createCompositionDto.getDescription())
                .build();
    }

    public List<GetCompositionDto> ListCompositionToListGetCompositionDto(List<Composition> compositions){
        return compositions.stream().map(this::CompositionToGetCompositionDto).collect(Collectors.toList());
    }
}
