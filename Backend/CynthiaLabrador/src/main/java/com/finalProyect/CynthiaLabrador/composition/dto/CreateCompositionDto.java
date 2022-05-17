package com.finalProyect.CynthiaLabrador.composition.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class CreateCompositionDto {

    @NotNull(message = "The name is mandatory")
    private String name;

    @NotNull(message = "The description is mandatory")
    private String description;

    @NotNull(message = "The author is mandatory")
    private String author;

    @NotNull(message = "The author is mandatory")
    private List<String> champions;

    private String date;
}