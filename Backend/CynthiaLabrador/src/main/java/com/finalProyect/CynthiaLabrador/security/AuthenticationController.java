package com.finalProyect.CynthiaLabrador.security;

import com.finalProyect.CynthiaLabrador.security.dto.JwtUsuarioResponse;
import com.finalProyect.CynthiaLabrador.security.dto.LoginDto;
import com.finalProyect.CynthiaLabrador.security.jwt.JwtProvider;
import com.finalProyect.CynthiaLabrador.users.dto.CreateUserDto;
import com.finalProyect.CynthiaLabrador.users.dto.GetUserDto;
import com.finalProyect.CynthiaLabrador.users.dto.UserDtoConverter;
import com.finalProyect.CynthiaLabrador.users.model.UserEntity;
import com.finalProyect.CynthiaLabrador.users.services.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final UserDtoConverter userDtoConverter;
    private final UserEntityService userEntityService;

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(),
                        loginDto.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        UserEntity userEntity = (UserEntity) authentication.getPrincipal();
        return ResponseEntity.status(HttpStatus.CREATED).body(convertUserToJwtUsuarioResponse(userEntity, jwt));
    }

    @PostMapping("auth/register")
    public ResponseEntity<GetUserDto> registerUser(@RequestPart("body") CreateUserDto createUserDto, @RequestPart("file") MultipartFile file) throws Exception {

        UserEntity user = userDtoConverter.UserEntityDtoToGetUser(createUserDto);

        if(userEntityService.existByNick(user.getNick()))
            return ResponseEntity.badRequest().build();

        UserEntity usuario = userEntityService.saveUser(createUserDto, file);
        if (usuario == null)
            return ResponseEntity.badRequest().build();
        else
            return ResponseEntity.status(HttpStatus.CREATED).body(userDtoConverter.UserEntityToGetUserDto(usuario));
    }

    private JwtUsuarioResponse convertUserToJwtUsuarioResponse(UserEntity user, String jwt) {
        return JwtUsuarioResponse.builder()
                .id(user.getId().toString())
                .nick(user.getNick())
                .name(user.getName())
                .city(user.getCity())
                .email(user.getEmail())
                .avatar(user.getAvatar())
                .role(user.getUserRoles().name())
                .token(jwt)
                .build();
    }
    private GetUserDto convertUserToGetUserDto(UserEntity user, String jwt) {
        return GetUserDto.builder()
                .id(user.getId())
                .lastName(user.getLastName())
                .nick(user.getNick())
                .name(user.getName())
                .email(user.getEmail())
                .avatar(user.getAvatar())
                .userRoles(user.getUserRoles().name())
                .build();
    }
}

