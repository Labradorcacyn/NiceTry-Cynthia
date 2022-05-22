package com.finalProyect.CynthiaLabrador.composition.dto;

import com.finalProyect.CynthiaLabrador.champions.dto.ChampionDtoConverter;
import com.finalProyect.CynthiaLabrador.composition.model.Composition;
import com.finalProyect.CynthiaLabrador.users.dto.UserDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CompositionDtoConverter {

    @Autowired
    private ChampionDtoConverter championDtoConverter;

    public GetCompositionDto compositionToGetCompositionDto(Composition composition){
        return GetCompositionDto.builder()
                .id(composition.getId())
                .name(composition.getName())
                .description(composition.getDescription())
                .authorNick(composition.getAuthor().getNick())
                .authorAvatar(composition.getAuthor().getAvatar())
                .date(composition.getCreateTime().toString())
                .votes(composition.getVotes())
                .comments(composition.getComments() != null ? composition.getComments().size() : 0)
                .champions(championDtoConverter.championsToGetChampionDto(composition.getChampions()))
                .build();
    }

    public Composition compositionDtoToGetComposition(CreateCompositionDto createCompositionDto){
        return Composition.builder()
                .name(createCompositionDto.getName())
                .description(createCompositionDto.getDescription())
                .build();
    }

    public List<GetCompositionDto> listCompositionToListGetCompositionDto(List<Composition> compositions){
        return compositions.stream().map(this::compositionToGetCompositionDto).collect(Collectors.toList());
    }
}
