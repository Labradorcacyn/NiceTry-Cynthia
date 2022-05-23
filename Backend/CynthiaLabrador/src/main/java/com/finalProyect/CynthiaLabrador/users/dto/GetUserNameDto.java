package com.finalProyect.CynthiaLabrador.users.dto;

import lombok.*;

import java.util.UUID;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class GetUserNameDto {
    private UUID id;
    private String nick;
    private String avatar;
}
