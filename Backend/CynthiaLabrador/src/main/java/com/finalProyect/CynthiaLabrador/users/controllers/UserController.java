package com.finalProyect.CynthiaLabrador.users.controllers;

import com.finalProyect.CynthiaLabrador.errors.excepciones.UserEntityException;
import com.finalProyect.CynthiaLabrador.follow.dto.CreatePeticionDto;
import com.finalProyect.CynthiaLabrador.follow.dto.GetPeticionDto;
import com.finalProyect.CynthiaLabrador.follow.dto.PeticionDtoConverter;
import com.finalProyect.CynthiaLabrador.follow.model.PeticionSeguimiento;
import com.finalProyect.CynthiaLabrador.follow.services.PeticionService;
import com.finalProyect.CynthiaLabrador.users.dto.CreateUserDto;
import com.finalProyect.CynthiaLabrador.users.dto.CreateUserDtoEdit;
import com.finalProyect.CynthiaLabrador.users.dto.GetUserDto;
import com.finalProyect.CynthiaLabrador.users.dto.UserDtoConverter;
import com.finalProyect.CynthiaLabrador.users.model.UserEntity;
import com.finalProyect.CynthiaLabrador.users.services.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final UserEntityService userEntityService;
    private final UserDtoConverter userDtoConverter;
    private final PeticionService peticionService;
    private final PeticionDtoConverter peticionDtoConverter;

    @PostMapping("auth/register")
    public ResponseEntity<GetUserDto> nuevoUser( @RequestPart("body") CreateUserDto createUserDto, @RequestPart("file")MultipartFile file) throws Exception {

       // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
        UserEntity usuario = userEntityService.saveUser(createUserDto, file);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDtoConverter.UserEntityToGetUserDto(usuario));
    }

    @GetMapping("follow/list")
    public ResponseEntity<List<GetPeticionDto>> listaPeticiones(){
        if (peticionService.findAll().isEmpty()){
            return ResponseEntity.notFound().build();
        }else{
            List<GetPeticionDto> list = peticionService.findAll().stream()
                    .map(peticionDtoConverter::PeticionToGetPeticionDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok().body(list);
        }
    }

    @PutMapping("profile/me")
    public ResponseEntity<Optional<GetUserDto>> actualizarPerfil (@AuthenticationPrincipal UserEntity userEntity, @RequestPart("user") CreateUserDtoEdit createUserDto, @RequestPart("file")MultipartFile file) throws Exception {

        return ResponseEntity.ok(userEntityService.actualizarPerfil(userEntity, createUserDto, file));
    }

    @PostMapping("follow/{nick}")
    public ResponseEntity<GetPeticionDto> realizarPeticion (@PathVariable String nick, @RequestPart("peticion") CreatePeticionDto createPeticionDto, @AuthenticationPrincipal UserEntity user){

        PeticionSeguimiento peticionSeguimiento = userEntityService.solicitud(user, createPeticionDto,nick);

        return  ResponseEntity.status(HttpStatus.CREATED).body(peticionDtoConverter.PeticionToGetPeticionDto(peticionSeguimiento));
    }

    @PostMapping("follow/accept/{id}")
    public ResponseEntity<?> aceptarPeticion(@PathVariable Long id, @AuthenticationPrincipal UserEntity userEntity){

        if (id.equals(null)){
            throw new UserEntityException("No se puede encontrar la petición solicitada");
        }else {

            userEntityService.aceptarSolicitud(userEntity, id);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
    }

    @PostMapping("follow/decline/{id}")
    public ResponseEntity<?> rechazarPeticion(@PathVariable Long id){

        if (id.equals(null)){
            throw new UserEntityException("No se puede encontrar la petición solicitada");
        }else {
            userEntityService.rechazarPeticion(id);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
    }
}