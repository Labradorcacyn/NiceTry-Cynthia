package com.finalProyect.CynthiaLabrador.users.services;

import com.finalProyect.CynthiaLabrador.composition.model.Composition;
import com.finalProyect.CynthiaLabrador.composition.repository.CompositionRepository;
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
import java.util.*;

@Service("userDetailService")
@RequiredArgsConstructor
public class UserEntityService extends BaseService<UserEntity, UUID, UserEntityRepository> implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final StorageService storageService;
    private final UserEntityRepository userEntityRepository;
    private final CompositionRepository compositionRepository;

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

    public UserEntity updateUser(UserEntity user, CreateUserDto u, MultipartFile file) throws Exception {
        List<String> extensiones = Arrays.asList("png", "gif", "jpg", "svg");
        String archivo = StringUtils.cleanPath(file.getOriginalFilename());
        String extension = StringUtils.getFilenameExtension(archivo);

        if (file.isEmpty()) {
            throw new FileNotFoundException();
        } else if (extensiones.contains(extension)) {

            String fileName = file.getOriginalFilename();
            String s = user.getAvatar().split("/")[4];

            if(!s.equals(fileName)){
                storageService.delete(s);
                String filename = storageService.escalar(file, 128);
                String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/download/")
                        .path(filename)
                        .toUriString().replace("10.0.2.2", "localhost");
                user.setAvatar(uri);
            }
            user.setName(u.getName());
            user.setCity(u.getCity());
            user.setLastName(u.getLastName());

            return save(user);
        } else {
            throw new UnsupportedMediaTypeException(extensiones, MultipartFile.class);
        }
    }

   public UserEntity findByNick(String nick) {
        return userEntityRepository.findByNick(nick).orElseThrow(() -> new SingleEntityNotFoundException(nick, UserEntity.class));
    }

    public void addCompositionToList(UserEntity user, UUID id) {
        Optional<Composition> composition = compositionRepository.findById(id);

        if (composition.isPresent() && !composition.get().getAuthor().getNick().equals(user.getNick())) {
            user.getCompositionsFav().add(composition.get());
            userEntityRepository.save(user);
        }
    }

    public List<UserEntity> findAllAdmins() {
        List<UserEntity> users = new ArrayList<>();
        userEntityRepository.findAll().stream().filter(user -> user.getUserRoles() == UserRoles.ADMIN).forEach(user -> {
            users.add(user);
        });

        return users;
    }

    public List<UserEntity> findAllUsers() {
        List<UserEntity> users = new ArrayList<>();
        userEntityRepository.findAll().stream().filter(user -> user.getUserRoles() == UserRoles.USER).forEach(user -> {
            users.add(user);
        });

        return users;
    }
}