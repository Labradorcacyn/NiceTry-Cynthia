package com.finalProyect.CynthiaLabrador.users.controllers;

import com.finalProyect.CynthiaLabrador.users.dto.CreateUserDto;
import com.finalProyect.CynthiaLabrador.users.dto.GetUserDto;
import com.finalProyect.CynthiaLabrador.users.model.UserEntity;
import com.finalProyect.CynthiaLabrador.users.services.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final UserEntityService userEntityService;

    @PutMapping("profile/me")
    public ResponseEntity<Optional<GetUserDto>> updateProfile (@AuthenticationPrincipal UserEntity userEntity, @RequestPart("user") CreateUserDto createUserDto, @RequestPart("file")MultipartFile file) throws Exception {

        return ResponseEntity.ok(userEntityService.actualizarPerfil(userEntity, createUserDto, file));
    }
}
