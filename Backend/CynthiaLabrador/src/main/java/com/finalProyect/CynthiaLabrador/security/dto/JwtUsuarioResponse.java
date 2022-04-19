package com.finalProyect.CynthiaLabrador.security.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtUsuarioResponse {

    private String id;
    private String nick;
    private String email;
    private String city;
    private String name;
    private String avatar;
    private String role;
    private String token;
}