package com.finalProyect.CynthiaLabrador.comments.dto;

import com.finalProyect.CynthiaLabrador.composition.model.Composition;
import com.finalProyect.CynthiaLabrador.users.model.UserEntity;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class CreateCommentDto {
    @NotNull
    private UserEntity author;

    @NotNull(message = "The description is mandatory")
    private Composition composition;

    @NotNull(message = "Write a comment")
    private String text;
}
