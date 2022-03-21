package com.finalProyect.CynthiaLabrador.users.dto;

import com.finalProyect.CynthiaLabrador.validation.anotaciones.UserUniqueNick;
import lombok.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserDto {

    private String nombre;
    private String apellidos;

    @UserUniqueNick(message = "El nombre de usuario ya está en uso")
    private String nick;
    @Email
    private String email;
    private LocalDate fechaNacimiento;
    private boolean rol;
    private String avatar;
    @NotNull
    private String password;
    @NotNull
    private String password2;
}
