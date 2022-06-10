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

@Service
@RequiredArgsConstructor
public class TraitsService extends BaseService<Traits, Long, TraitsRepository> {
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

    public Traits createTrait(CreateTraitsDto createTraitsDto) throws Exception {
        Traits trait = Traits.builder()
                .name(createTraitsDto.getName())
                .description(createTraitsDto.getDescription())
                .build();
        return save(trait);
    }

    public Traits updateTrait(Traits trait, CreateTraitsDto createTraitsDto) throws Exception {
        trait.setName(createTraitsDto.getName());
        trait.setDescription(createTraitsDto.getDescription());
        return save(trait);
    }

    public void deleteTrait(Traits trait) throws TraitNotFoundException, IOException {
        this.repository.delete(trait);
    }

}
