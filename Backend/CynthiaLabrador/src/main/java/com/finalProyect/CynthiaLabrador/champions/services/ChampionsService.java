package com.finalProyect.CynthiaLabrador.champions.services;

import com.finalProyect.CynthiaLabrador.champions.dto.CreateChampionDto;
import com.finalProyect.CynthiaLabrador.champions.model.Champion;
import com.finalProyect.CynthiaLabrador.champions.repository.ChampionsRepository;
import com.finalProyect.CynthiaLabrador.errors.excepciones.ChampionNotFoundException;
import com.finalProyect.CynthiaLabrador.errors.excepciones.UnsupportedMediaTypeException;
import com.finalProyect.CynthiaLabrador.errors.excepciones.UserEntityException;
import com.finalProyect.CynthiaLabrador.file.services.StorageService;
import com.finalProyect.CynthiaLabrador.file.services.base.BaseService;
import com.finalProyect.CynthiaLabrador.traits.model.Traits;
import com.finalProyect.CynthiaLabrador.traits.services.TraitsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ChampionsService extends BaseService<Champion, UUID, ChampionsRepository> {

    private final StorageService storageService;
    private final TraitsService traitsService;

    public Champion getChampionById(UUID id) {
        return this.repository.findById(id).orElseThrow(() -> new UserEntityException("Champion no encontrado"));
    }

    public boolean existByName(String name) {
        return this.repository.existsByName(name);
    }

    public Champion findByName(String name) throws ChampionNotFoundException {
        return this.repository.findByName(name).orElseThrow(() -> new ChampionNotFoundException(name + " no encontrado"));
    }

    public List<Champion> getAllChampions() {
        return this.repository.findAll();
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
                        .build();

                List<Traits> traits = new ArrayList<>();

                createChampionDto.getTraitsUuid().forEach(trait -> {
                Optional<Traits> traitOptional = traitsService.findById(trait);
                if (traitOptional != null ) {
                    traits.add(traitOptional.get());
                }
                });
                champion.setTraits(traits);
                return save(champion);
        } else {
            throw new UnsupportedMediaTypeException(extensiones, MultipartFile.class);
        }
    }

    public Champion updateChampion(Champion champion, CreateChampionDto updateChampionDto, MultipartFile file) throws Exception {

        List<String> extensiones = Arrays.asList("png", "gif", "jpg", "svg");
        String archivo = StringUtils.cleanPath(file.getOriginalFilename());
        String extension = StringUtils.getFilenameExtension(archivo);

        if (file.isEmpty()) {
            throw new FileNotFoundException();
        } else if (extensiones.contains(extension)) {

            String fileName = file.getOriginalFilename();
            String s = champion.getAvatar().split("/")[4];

            if(!s.equals(fileName)){
                storageService.delete(s);
                String filename = storageService.escalar(file, 128);
                String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/download/")
                        .path(filename)
                        .toUriString().replace("10.0.2.2", "localhost");
                champion.setAvatar(uri);
            }
            champion.setName(updateChampionDto.getName());
            champion.setCost(updateChampionDto.getCost());
            List<Traits> traits = new ArrayList<>();
            updateChampionDto.getTraitsUuid().forEach(trait -> {
                Optional<Traits> t = traitsService.findById(trait);
                if (t.isPresent()) {
                    traits.add(t.get());
                }
            });

            champion.setTraits(traits);
            return save(champion);
        } else {
            throw new UnsupportedMediaTypeException(extensiones, MultipartFile.class);
        }
    }

    public Champion deleteChampion(Champion champion) throws ChampionNotFoundException, IOException {
        storageService.delete(champion.getAvatar().split("/")[4]);
        this.repository.delete(champion);
        return champion;
    }
}
