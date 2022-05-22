package com.finalProyect.CynthiaLabrador.composition.dto;

import com.finalProyect.CynthiaLabrador.champions.dto.GetChampionDto;
import com.finalProyect.CynthiaLabrador.users.dto.GetUserDtoWithoutList;
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
    private String authorNick;
    private String authorAvatar;
    private String date;
    private List<GetChampionDto> champions;
    private List<String> votes;
    private Integer comments;
}

