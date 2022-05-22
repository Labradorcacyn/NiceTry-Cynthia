package com.finalProyect.CynthiaLabrador.traits.dto;

import com.finalProyect.CynthiaLabrador.traits.model.Traits;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TraitsDtoConverter {
    public GetTraitsDto traitToGetTraitDto(Traits traits){
        return GetTraitsDto.builder()
                .id(traits.getId())
                .name(traits.getName())
                .avatar(traits.getAvatar())
                .description(traits.getDescription())
                .build();
    }

    public Traits traitDtoToGetTrait(CreateTraitsDto traitsDto){
        return Traits.builder()
                .name(traitsDto.getName())
                .avatar(traitsDto.getAvatar())
                .description(traitsDto.getDescription())
                .build();
    }

    public Traits TraitDtoToTrait(GetTraitsDto traitsDto){
        return Traits.builder()
                .name(traitsDto.getName())
                .avatar(traitsDto.getAvatar())
                .description(traitsDto.getDescription())
                .build();
    }

    public List<GetTraitsDto> traitsToGetTraitsDto(List<Traits> traits){
        return traits.stream()
                .map(trait -> traitToGetTraitDto(trait))
                .collect(Collectors.toList());
    }

    public List<Traits> traitsDtoToGetTraits(List<GetTraitsDto> traitsDto){
        return traitsDto.stream()
                .map(traitDto -> TraitDtoToTrait(traitDto))
                .collect(Collectors.toList());
    }
}
