package com.finalProyect.CynthiaLabrador.champions.services;

import com.finalProyect.CynthiaLabrador.champions.dto.CreateChampionDto;
import com.finalProyect.CynthiaLabrador.champions.model.Champion;
import com.finalProyect.CynthiaLabrador.champions.repository.ChampionsRepository;
import com.finalProyect.CynthiaLabrador.errors.excepciones.UnsupportedMediaTypeException;
import com.finalProyect.CynthiaLabrador.errors.excepciones.UserEntityException;
import com.finalProyect.CynthiaLabrador.file.services.StorageService;
import com.finalProyect.CynthiaLabrador.file.services.base.BaseService;
import com.finalProyect.CynthiaLabrador.users.model.UserEntity;
import com.finalProyect.CynthiaLabrador.users.model.UserRoles;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChampionsService extends BaseService<Champion, UUID, ChampionsRepository> {
    private final StorageService storageService;
    private final ChampionsRepository championsRepository;

    public UserDetails loadChampionByname(String name) throws UsernameNotFoundException {
        return this.repository.findFirstByName(name).orElseThrow(() -> new UsernameNotFoundException(name + " no encontrado"));
    }

    public boolean existByName(String name) {
        return this.repository.existsByName(name);
    }

    public Champion saveChampion(CreateChampionDto createChampionDto, MultipartFile file) throws Exception {

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

                Champion champion = Champion.builder()
                        .name(createChampionDto.getName())
                        .avatar(uri)
                        .cost(createChampionDto.getCost())
                        //.traits(createChampionDto.getTraits())
                        .build();
                return save(champion);
        } else {
            throw new UnsupportedMediaTypeException(extensiones, MultipartFile.class);
        }
    }
}
