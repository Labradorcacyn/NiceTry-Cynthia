package com.finalProyect.CynthiaLabrador.comments.dto;

import com.finalProyect.CynthiaLabrador.composition.dto.GetCompositionDto;
import com.finalProyect.CynthiaLabrador.users.dto.GetUserNameDto;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class GetCommentDto {
    private UUID id;
    private GetUserNameDto author;
    private GetCompositionDto composition;
    private String text;
    private List<String> votes;
}
