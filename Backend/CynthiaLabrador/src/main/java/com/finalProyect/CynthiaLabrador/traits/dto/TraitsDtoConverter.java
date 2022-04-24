package com.finalProyect.CynthiaLabrador.traits.dto;

import com.finalProyect.CynthiaLabrador.traits.model.Traits;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TraitsDtoConverter {
    public GetTraitsDto TraitToGetTraitDto(Traits traits){
        return GetTraitsDto.builder()
                .id(traits.getId())
                .name(traits.getName())
                .avatar(traits.getAvatar())
                .description(traits.getDescription())
                .build();
    }

    public Traits TraitDtoToGetTrait(CreateTraitsDto traitsDto){
        return Traits.builder()
                .name(traitsDto.getName())
                .avatar(traitsDto.getAvatar())
                .description(traitsDto.getDescription())
                .build();
    }

    public List<GetTraitsDto> TraitsToGetTraitsDto(List<Traits> traits){
        return traits.stream()
                .map(trait -> TraitToGetTraitDto(trait))
                .collect(Collectors.toList());
    }
}
