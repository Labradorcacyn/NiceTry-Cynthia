package com.finalProyect.CynthiaLabrador.traits.dto;
import com.finalProyect.CynthiaLabrador.validation.anotaciones.TraitsUniqueName;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class CreateTraitsDto {
    @TraitsUniqueName()
    @NotNull(message = "Name is required")
    private String name;

    @NotNull(message = "Description is required")
    private String description;

    @NotNull(message = "Image is required")
    private String avatar;
}