package com.finalProyect.CynthiaLabrador.users.dto;

import com.finalProyect.CynthiaLabrador.validation.anotaciones.UserUniqueNick;
import lombok.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class CreateUserDto {

    private String name;

    private String lastName;

    @UserUniqueNick(message = "El nombre de usuario ya está en uso")
    private String nick;

    @Email
    private String email;

    private boolean rol;

    private String avatar;

    @NotNull
    private String password;

    @NotNull
    private String password2;

    private String city;
}
