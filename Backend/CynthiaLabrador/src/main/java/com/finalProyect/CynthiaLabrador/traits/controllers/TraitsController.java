package com.finalProyect.CynthiaLabrador.traits.controllers;

import com.finalProyect.CynthiaLabrador.errors.excepciones.TraitNotFoundException;
import com.finalProyect.CynthiaLabrador.traits.dto.CreateTraitsDto;
import com.finalProyect.CynthiaLabrador.traits.dto.GetTraitsDto;
import com.finalProyect.CynthiaLabrador.traits.dto.TraitsDtoConverter;
import com.finalProyect.CynthiaLabrador.traits.model.Traits;
import com.finalProyect.CynthiaLabrador.traits.services.TraitsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
@CrossOrigin(origins = "http://localhost:4200")
public class TraitsController {

    private final TraitsService traitsService;
    private final TraitsDtoConverter traitsDtoConverter;

    @PostMapping("trait/create")
    public ResponseEntity<GetTraitsDto> createTrait(@RequestPart("body") CreateTraitsDto createTraitsDto,
                                                    @RequestPart("file") MultipartFile file) throws Exception {

        Traits trait = traitsDtoConverter.TraitDtoToGetTrait(createTraitsDto);

        if (traitsService.existByName(trait.getName()))
            return ResponseEntity.badRequest().build();

        Traits saveTrait = traitsService.createTrait(createTraitsDto, file);
        if (saveTrait == null)
            return ResponseEntity.badRequest().build();
        else
            return ResponseEntity.status(HttpStatus.CREATED).body(traitsDtoConverter.TraitToGetTraitDto(saveTrait));
    }

    @GetMapping("trait/name/{name}")
    public ResponseEntity<GetTraitsDto> getTraitByName(@PathVariable String name) throws TraitNotFoundException, FileNotFoundException {
        String[] nameArray = name.split("");
        for (int i = 0; i < nameArray.length; i++) {
            nameArray[i] = nameArray[i].toLowerCase();
        }
        nameArray[0] = nameArray[0].toUpperCase();
        name = String.join("", nameArray);
        Traits traits = traitsService.findFirstByName(name);
        return ResponseEntity.ok(traitsDtoConverter.TraitToGetTraitDto(traits));
    }

    @GetMapping("traits")
    public ResponseEntity<List<GetTraitsDto>> getAllTraits() {
        List<Traits> traits = traitsService.getTraits();
        return ResponseEntity.ok(traitsDtoConverter.TraitsToGetTraitsDto(traits));
    }

    @PutMapping("trait/update/{name}")
    public ResponseEntity<GetTraitsDto> updateTrait(@PathVariable String name,
                                                         @RequestPart("body") CreateTraitsDto updateTraitsDto,
                                                         @RequestPart("file") MultipartFile file) throws Exception {

        Traits trait = traitsService.updateTrait(name, updateTraitsDto, file);
        return ResponseEntity.ok(traitsDtoConverter.TraitToGetTraitDto(trait));
    }

    @DeleteMapping("trait/delete/{name}")
    public ResponseEntity<String> deleteTrait(@PathVariable String name) throws IOException {
        traitsService.deleteTrait(name);
        return ResponseEntity.ok("Trait deleted");
    }
}
