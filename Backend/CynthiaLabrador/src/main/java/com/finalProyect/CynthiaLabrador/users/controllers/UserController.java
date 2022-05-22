package com.finalProyect.CynthiaLabrador.users.controllers;

import com.finalProyect.CynthiaLabrador.composition.model.Composition;
import com.finalProyect.CynthiaLabrador.users.dto.CreateUserDto;
import com.finalProyect.CynthiaLabrador.users.dto.GetUserDto;
import com.finalProyect.CynthiaLabrador.users.dto.GetUserDtoWithoutList;
import com.finalProyect.CynthiaLabrador.users.dto.UserDtoConverter;
import com.finalProyect.CynthiaLabrador.users.model.UserEntity;
import com.finalProyect.CynthiaLabrador.users.services.UserEntityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Controlador de los usuarios")
public class UserController {

    private final UserEntityService userEntityService;
    private final UserDtoConverter userDtoConverter;

    @Operation(summary = "Obtener todos los usuarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado los usuarios",
                    content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = "No se encuentra la lista de usuarios",
                    content = @Content)
    })
    @GetMapping("/users")
    public ResponseEntity<List<GetUserDtoWithoutList>> getAllUsers() {

        List<UserEntity> users = userEntityService.findAllUsers();

        if (users.isEmpty()) {
            return ResponseEntity.notFound().build();
        }   else{
            return ResponseEntity.ok(users
                    .stream()
                    .map(userDtoConverter::UserEntityToGetUserDtoWithoutList)
                    .collect(java.util.stream.Collectors.toList()));
        }
    }
    @Operation(summary = "Obtener todos los administradores")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado los administradores",
                    content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = "No se encuentra la lista de administradores",
                    content = @Content)
    })
    @GetMapping("/admins")
    public ResponseEntity<List<GetUserDtoWithoutList>> getAllAdmins() {

        List<UserEntity> admins = userEntityService.findAllAdmins();

        if (admins.isEmpty()) {
            return ResponseEntity.notFound().build();
        }   else{
            return ResponseEntity.ok(admins
                    .stream()
                    .map(userDtoConverter::UserEntityToGetUserDtoWithoutList)
                    .collect(java.util.stream.Collectors.toList()));
        }
    }

    @Operation(summary = "Editar mi perfil de usuario")
    @ApiResponse(responseCode = "200", description = "Se ha creado correctamente",
            content = { @Content(mediaType = "application/json")})
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
