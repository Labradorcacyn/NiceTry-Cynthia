package com.finalProyect.CynthiaLabrador.users.controllers;

import com.finalProyect.CynthiaLabrador.composition.model.Composition;
import com.finalProyect.CynthiaLabrador.users.dto.CreateUserDto;
import com.finalProyect.CynthiaLabrador.users.dto.GetUserDto;
import com.finalProyect.CynthiaLabrador.users.dto.GetUserDtoWithoutList;
import com.finalProyect.CynthiaLabrador.users.dto.UserDtoConverter;
import com.finalProyect.CynthiaLabrador.users.model.UserEntity;
import com.finalProyect.CynthiaLabrador.users.services.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final UserEntityService userEntityService;
    private final UserDtoConverter userDtoConverter;

    @GetMapping("/users")
    public ResponseEntity<List<GetUserDtoWithoutList>> getAllUsers() {
        return ResponseEntity.ok(userEntityService.findAllUsers()
                .stream()
                .map(userDtoConverter::UserEntityToGetUserDtoWithoutList)
                .collect(java.util.stream.Collectors.toList()));
    }

    @GetMapping("/admins")
    public ResponseEntity<List<GetUserDtoWithoutList>> findAllAdmins() {
        return ResponseEntity.ok(userEntityService.findAllUsers()
                .stream()
                .map(userDtoConverter::UserEntityToGetUserDtoWithoutList)
                .collect(java.util.stream.Collectors.toList()));
    }

    @PutMapping("profile/edit")
    public ResponseEntity<GetUserDto> updateProfile (@AuthenticationPrincipal UserEntity userEntity, @RequestPart("user") CreateUserDto createUserDto, @RequestPart("file")MultipartFile file) throws Exception {

        UserEntity user = userEntityService.updateUser(userEntity, createUserDto, file);
        return ResponseEntity.ok(userDtoConverter
                .UserEntityToGetUserDto(user));
    }

    @PutMapping("/composition/{id}/add")
    public ResponseEntity<?> addCompositionToList (@AuthenticationPrincipal UserEntity userEntity, @PathVariable UUID id) {
        userEntityService.addCompositionToList(userEntity, id);
        List<Composition> fav = userEntity.getCompositionsFav();
        AtomicBoolean exist = new AtomicBoolean(false);
        userEntity.getCompositions().forEach(composition -> {

            if(composition.getId().equals(id)) {
                exist.set(true);
            }
        });
        if(exist.get()) {
            return ResponseEntity.ok("Composition added to favorites");
        }   else {
            return ResponseEntity.badRequest().body("Composition not added to favorites");
        }
    }
}
