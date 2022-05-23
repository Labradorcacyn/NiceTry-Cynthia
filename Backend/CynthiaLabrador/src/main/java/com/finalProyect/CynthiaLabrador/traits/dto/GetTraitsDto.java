package com.finalProyect.CynthiaLabrador.traits.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class GetTraitsDto {
    private Long id;
    private String name;
    private String description;
    private String avatar;

}
