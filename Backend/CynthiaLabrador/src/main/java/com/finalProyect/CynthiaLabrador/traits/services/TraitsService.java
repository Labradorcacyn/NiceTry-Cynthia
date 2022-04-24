package com.finalProyect.CynthiaLabrador.traits.services;

import com.finalProyect.CynthiaLabrador.errors.excepciones.TraitNotFoundException;
import com.finalProyect.CynthiaLabrador.errors.excepciones.UnsupportedMediaTypeException;
import com.finalProyect.CynthiaLabrador.file.services.StorageService;
import com.finalProyect.CynthiaLabrador.file.services.base.BaseService;
import com.finalProyect.CynthiaLabrador.traits.dto.CreateTraitsDto;
import com.finalProyect.CynthiaLabrador.traits.model.Traits;
import com.finalProyect.CynthiaLabrador.traits.repository.TraitsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TraitsService extends BaseService<Traits, UUID, TraitsRepository> {
    private final StorageService storageService;
    private final TraitsRepository traitsRepository;

    public boolean existByName(String name) {
        return traitsRepository.existsByName(name);
    }

    public Traits findFirstByName(String name) throws FileNotFoundException {
        return traitsRepository.findFirstByName(name).orElseThrow(() -> new FileNotFoundException("Can't find trait with name: " + name));
    }

    public List<Traits> getTraits(){
        return traitsRepository.findAll();
    }

    public Traits createTrait(CreateTraitsDto createTraitsDto, MultipartFile file) throws Exception {

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

            Traits trait = Traits.builder()
                    .name(createTraitsDto.getName())
                    .avatar(uri)
                    .description(createTraitsDto.getDescription())
                    .build();
            return save(trait);
        } else {
            throw new UnsupportedMediaTypeException(extensiones, MultipartFile.class);
        }
    }

    public Traits updateTrait(String name, CreateTraitsDto createTraitsDto, MultipartFile file) throws Exception {

        List<String> extensiones = Arrays.asList("png", "gif", "jpg", "svg");
        String archivo = StringUtils.cleanPath(file.getOriginalFilename());
        String extension = StringUtils.getFilenameExtension(archivo);

        if (file.isEmpty()) {
            throw new FileNotFoundException();
        } else if (extensiones.contains(extension)) {

            Traits trait = findFirstByName(name);
            String fileName = file.getOriginalFilename();
            String s = trait.getAvatar().split("/")[4];

            if(!s.equals(fileName)){
                storageService.delete(s);
                String filename = storageService.escalar(file, 128);
                String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/download/")
                        .path(filename)
                        .toUriString().replace("10.0.2.2", "localhost");
                trait.setAvatar(uri);
            }
            trait.setName(createTraitsDto.getName());
            trait.setDescription(createTraitsDto.getDescription());
            return save(trait);
        } else {
            throw new UnsupportedMediaTypeException(extensiones, MultipartFile.class);
        }
    }

    public Traits deleteTrait(String name) throws TraitNotFoundException, IOException {
        Traits trait = findFirstByName(name);
        storageService.delete(trait.getAvatar().split("/")[4]);
        this.repository.delete(trait);
        return trait;
    }

}
