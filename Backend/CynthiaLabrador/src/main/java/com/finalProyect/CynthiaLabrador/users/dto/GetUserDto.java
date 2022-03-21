package com.finalProyect.CynthiaLabrador.users.dto;

import lombok.*;
import java.time.LocalDate;
import java.util.UUID;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class GetUserDto {

    private UUID id;
    private String nombre;
    private String apellidos;
    private String nick;
    private LocalDate fechaNacimiento;
    private String email;
    private String avatar;
    private String userRoles;
}
