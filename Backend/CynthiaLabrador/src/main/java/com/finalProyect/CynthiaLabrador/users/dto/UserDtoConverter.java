package com.finalProyect.CynthiaLabrador.users.dto;

import com.finalProyect.CynthiaLabrador.composition.dto.CompositionDtoConverter;
import com.finalProyect.CynthiaLabrador.users.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserDtoConverter {
    @Autowired
    private CompositionDtoConverter compositionDtoConverter;

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
                .compositionList(user.getCompositions() != null ? user.getCompositions() .stream().map(c ->
                        compositionDtoConverter.CompositionToGetCompositionDto(c)).collect(Collectors.toList()) : null)
                .build();
    }

    public GetUserDtoWithoutList UserEntityToGetUserDtoWithoutList(UserEntity user){
        return GetUserDtoWithoutList.builder()
                .id(user.getId())
                .name(user.getName())
                .lastName(user.getLastName())
                .nick(user.getNick())
                .avatar(user.getAvatar())
                .build();
    }

    public GetUserNameDto userEntityToGetUserNameDto(UserEntity user){
        return GetUserNameDto.builder()
                .id(user.getId())
                .nick(user.getName())
                .avatar(user.getAvatar())
                .build();
    }

    public UserEntity UserEntityDtoToGetUser(CreateUserDto createUserDto){
        return UserEntity.builder()
                .nick(createUserDto.getNick())
                .build();
    }

    public List<GetUserDtoWithoutList> UserEntityListToGetUserDtoList(List<UserEntity> userList){
        return userList.stream().map(user -> UserEntityToGetUserDtoWithoutList(user)).collect(Collectors.toList());
    }
}