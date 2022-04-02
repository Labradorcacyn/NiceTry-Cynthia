package com.finalProyect.CynthiaLabrador.users.dto;

import lombok.*;
import java.util.UUID;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class GetUserDto {

    private UUID id;
    private String name;
    private String lastName;
    private String nick;
    private String email;
    private String avatar;
    private String userRoles;
    private String city;
}
