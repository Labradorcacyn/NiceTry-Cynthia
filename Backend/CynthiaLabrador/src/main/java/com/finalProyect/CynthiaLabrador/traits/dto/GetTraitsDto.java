package com.finalProyect.CynthiaLabrador.traits.dto;

import lombok.*;

import java.util.UUID;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class GetTraitsDto {
    private UUID id;
    private String name;
    private String description;
    private String avatar;

}
