package com.finalProyect.CynthiaLabrador.users.dto;

import com.finalProyect.CynthiaLabrador.users.model.UserEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component

public class UserDtoConverter {


    public GetUserDto UserEntityToGetUserDto(UserEntity user){
        return GetUserDto.builder()
                .id(user.getId())
                .nombre(user.getNombre())
                .apellidos(user.getApellidos())
                .nick(user.getNick())
                .fechaNacimiento(user.getFechaNacimiento())
                .email(user.getEmail())
                .avatar(user.getAvatar())
                .userRoles(user.getUserRoles().name())
                .build();
    }

    public GetUserDtoWithFollowers UserEntityToGetUserDtoWithFollowers(Optional<UserEntity> user){

        return GetUserDtoWithFollowers.builder()
                .id(user.get().getId())
                .nombre(user.get().getNombre())
                .apellidos(user.get().getApellidos())
                .nick(user.get().getNick())
                .fechaNacimiento(user.get().getFechaNacimiento())
                .email(user.get().getEmail())
                .avatar(user.get().getAvatar())
                .userRoles(user.get().getUserRoles().name())
                .followers(user.get().getFollowers().stream().map(p -> p.getNick()).toList())
                .peticiones(user.get().getPeticionSeguimientoList().size())
                .build();
    }

    public GetUserDtoWithList UserEntityToGetUserDtoWithLists(Optional<UserEntity> user/*, List<Champ> lista*/){

        return GetUserDtoWithList.builder()
                .id(user.get().getId())
                .nombre(user.get().getNombre())
                .apellidos(user.get().getApellidos())
                .nick(user.get().getNick())
                .fechaNacimiento(user.get().getFechaNacimiento())
                .email(user.get().getEmail())
                .avatar(user.get().getAvatar())
                .userRoles(user.get().getUserRoles().name())
                .followers(user.get().getFollowers().stream().map(p -> p.getNick()).toList())
               // .publicaciones(lista.stream().map(p -> new GetPublicacion2(p.getId(), p.getTitulo(), p.getTexto(), p.getFile(), p.getFechaPublicacion(), p.getEstadoPublicacion())).toList())
                .peticiones(user.get().getPeticionSeguimientoList().size())
                .build();
    }
}