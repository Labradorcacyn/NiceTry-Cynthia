package com.finalProyect.CynthiaLabrador.users.dto;

import com.finalProyect.CynthiaLabrador.users.model.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserDtoConverter {

    public GetUserDto UserEntityToGetUserDto(UserEntity user){
        return GetUserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .lastName(user.getLastName())
                .nick(user.getNick())
                .email(user.getEmail())
                .avatar(user.getAvatar())
                .userRoles(user.getUserRoles().name())
                .city(user.getCity())
                .build();
    }

    public UserEntity UserEntityDtoToGetUser(CreateUserDto createUserDto){
        return UserEntity.builder()
                .nick(createUserDto.getNick())
                .build();
    }
}