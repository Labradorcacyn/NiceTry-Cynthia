package com.finalProyect.CynthiaLabrador.users.services;

import com.finalProyect.CynthiaLabrador.errors.excepciones.*;
import com.finalProyect.CynthiaLabrador.file.services.StorageService;
import com.finalProyect.CynthiaLabrador.file.services.base.BaseService;
import com.finalProyect.CynthiaLabrador.users.dto.*;
import com.finalProyect.CynthiaLabrador.users.model.UserEntity;
import com.finalProyect.CynthiaLabrador.users.model.UserRoles;
import com.finalProyect.CynthiaLabrador.users.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service("userDetailService")
@RequiredArgsConstructor
public class UserEntityService extends BaseService<UserEntity, UUID, UserEntityRepository> implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final StorageService storageService;
    private final UserEntityRepository userEntityRepository;
    private final UserDtoConverter userDtoConverter;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.repository.findFirstByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email + " no encontrado"));
    }

    public boolean existByNick(String nick) {
        return this.repository.existsByNick(nick);
    }

    public List<UserEntity> loadUserByRole(UserRoles userRoles) throws UsernameNotFoundException {
        return this.repository.findByUserRoles(userRoles).orElseThrow(() -> new UsernameNotFoundException(userRoles + " no encontrado"));
    }

    public UserEntity loadUserById(UUID uuid) throws UsernameNotFoundException {
        return this.repository.findById(uuid).orElseThrow(() -> new UsernameNotFoundException(uuid + " no encontrado"));
    }

    public UserEntity saveUser(CreateUserDto userDto, MultipartFile file) throws Exception {

        List<String> extensiones = Arrays.asList("png", "gif", "jpg", "svg");

        String archivo = StringUtils.cleanPath(file.getOriginalFilename());

        String extension = StringUtils.getFilenameExtension(archivo);
        if (file.isEmpty()) {
            throw new FileNotFoundException();
        } else if (extensiones.contains(extension)) {

            String filename = storageService.escalar(file, 128);

            String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/download/")
                    .path(filename)
                    .toUriString().replace("10.0.2.2", "localhost");

            if (userDto.getPassword().equalsIgnoreCase(userDto.getPassword2())) {
                UserEntity userEntity = UserEntity.builder()
                        .nick(userDto.getNick())
                        .name(userDto.getName())
                        .lastName(userDto.getLastName())
                        .email(userDto.getEmail())
                        .avatar(uri)
                        .password(passwordEncoder.encode(userDto.getPassword()))
                        .userRoles(userDto.isRol() ? UserRoles.USER : UserRoles.ADMIN)
                        .city(userDto.getCity())
                        .build();
                return save(userEntity);
            } else {
                throw new UserEntityException("Las contraseñas no coinciden");
            }

        } else {
            throw new UnsupportedMediaTypeException(extensiones, MultipartFile.class);
        }
    }

    public Optional<GetUserDto> actualizarPerfil(UserEntity user, CreateUserDto u, MultipartFile file) throws Exception {

        List<String> extensiones = Arrays.asList("png", "gif", "jpg", "svg");

        String archivo = StringUtils.cleanPath(file.getOriginalFilename());

        String extension = StringUtils.getFilenameExtension(archivo);

        if (file.isEmpty()) {

            Optional<UserEntity> data = userEntityRepository.findById(user.getId());

            if (data.isEmpty()) {
                throw new SingleEntityNotFoundException(user.getId().toString(), UserEntity.class);
            }

            return data.map(m -> {
                m.setName(u.getName());
                m.setLastName(user.getLastName());
                m.setUserRoles(u.isRol() ? UserRoles.USER : UserRoles.ADMIN);
                m.setNick(u.getNick());
                m.setAvatar(m.getAvatar());
                m.setEmail(u.getEmail());
                userEntityRepository.save(m);
                return userDtoConverter.UserEntityToGetUserDto(m);
            });

        } else if (extensiones.contains(extension)){

            Optional<UserEntity> data = userEntityRepository.findById(user.getId());

            if (data.isEmpty()) {
                throw new SingleEntityNotFoundException(user.getId().toString(), UserEntity.class);
            }

            String name = StringUtils.cleanPath(String.valueOf(data.get().getAvatar())).replace("https://nicetry-api.herokuapp.com/download/", "")
                    .replace("%20", " ");

            Path pa = storageService.load(name);

            String filename = StringUtils.cleanPath(String.valueOf(pa)).replace("https://nicetry-api.herokuapp.com/download/", "")
                    .replace("%20", " ");

            Path path = Paths.get(filename);

            storageService.deleteFile(path);

            String avatar = storageService.escalar(file, 128);


            String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/download/")
                    .path(avatar)
                    .toUriString();

            return data.map(m -> {
                m.setName(u.getName());
                m.setLastName(user.getLastName());
                m.setUserRoles(u.isRol() ? UserRoles.USER : UserRoles.ADMIN);
                m.setNick(u.getNick());
                m.setAvatar(uri);
                m.setEmail(u.getEmail());
                userEntityRepository.save(m);
                return userDtoConverter.UserEntityToGetUserDto(m);
            });

        }else {
            throw new UnsupportedMediaTypeException(extensiones, MultipartFile.class);
        }
    }
}