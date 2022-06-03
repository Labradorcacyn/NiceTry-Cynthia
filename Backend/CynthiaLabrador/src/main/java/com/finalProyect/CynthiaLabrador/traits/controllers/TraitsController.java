package com.finalProyect.CynthiaLabrador.traits.controllers;

import com.finalProyect.CynthiaLabrador.errors.excepciones.TraitNotFoundException;
import com.finalProyect.CynthiaLabrador.traits.dto.CreateTraitsDto;
import com.finalProyect.CynthiaLabrador.traits.dto.GetTraitsDto;
import com.finalProyect.CynthiaLabrador.traits.dto.TraitsDtoConverter;
import com.finalProyect.CynthiaLabrador.traits.model.Traits;
import com.finalProyect.CynthiaLabrador.traits.services.TraitsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Controlador de las características de los campeones")
public class TraitsController {

    private final TraitsService traitsService;
    private final TraitsDtoConverter traitsDtoConverter;

    @Operation(summary = "Crea una nueva característica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha creado correctamente",
                    content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha podido crear la característica",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "El nombre de la característica ya existe",
                    content = @Content)
    })
    @PostMapping("trait/create")
    public ResponseEntity<GetTraitsDto> createTrait(@RequestBody CreateTraitsDto createTraitsDto) throws Exception {

        Traits trait = traitsDtoConverter.traitDtoToGetTrait(createTraitsDto);

        if (traitsService.existByName(trait.getName()))
            return ResponseEntity.badRequest().build();

        Traits saveTrait = traitsService.createTrait(createTraitsDto);
        if (saveTrait == null)
            return ResponseEntity.badRequest().build();
        else
            return ResponseEntity.status(HttpStatus.CREATED).body(traitsDtoConverter.traitToGetTraitDto(saveTrait));
    }
    @Operation(summary = "Encontrar una característica por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado",
                    content = { @Content(mediaType = "application/json")}),
            @ApiResponse(description = "No se encuentra la característica",
                    responseCode = "404",
                    content = @Content)
    })
    @GetMapping("trait/id/{id}")
    public ResponseEntity<GetTraitsDto> getTraitById(@PathVariable Long id) throws IOException {

        Optional<Traits> traits = traitsService.findById(id);
        if (traits.isPresent()) {
            return ResponseEntity.ok(traitsDtoConverter.traitToGetTraitDto(traits.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @Operation(summary = "Encontrar una característica por nombre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado",
                    content = { @Content(mediaType = "application/json")}),
            @ApiResponse(description = "No se encuentra la característica",
                    responseCode = "404",
                    content = @Content)
    })
    @GetMapping("trait/name/{name}")
    public ResponseEntity<GetTraitsDto> getTraitByName(@PathVariable String name) throws TraitNotFoundException, FileNotFoundException {
        String[] nameArray = name.split("");
        for (int i = 0; i < nameArray.length; i++) {
            nameArray[i] = nameArray[i].toLowerCase();
        }
        nameArray[0] = nameArray[0].toUpperCase();
        name = String.join("", nameArray);
        Traits traits = traitsService.findFirstByName(name);
        if(traits != null)
            return ResponseEntity.ok(traitsDtoConverter.traitToGetTraitDto(traits));
        else
            return ResponseEntity.notFound().build();
    }
    @Operation(summary = "Encontrar todas las características")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado",
                    content = { @Content(mediaType = "application/json")}),
            @ApiResponse(description = "No se encuentra las características",
                    responseCode = "404",
                    content = @Content)
    })
    @GetMapping("traits")
    public ResponseEntity<List<GetTraitsDto>> getAllTraits() {
        List<Traits> traits = traitsService.getTraits();
        if(traits.isEmpty())
            return ResponseEntity.notFound().build();
        else
        return ResponseEntity.ok(traitsDtoConverter.traitsToGetTraitsDto(traits));
    }
    @Operation(summary = "Editar una característica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha editado correctamente",
                    content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400",
                    description = "No se encuentra la característica",
                    content = @Content)
    })
    @PutMapping("trait/update/{id}")
    public ResponseEntity<GetTraitsDto> updateTrait(@PathVariable Long id,
                                                         @RequestBody CreateTraitsDto updateTraitsDto) throws Exception {
        Optional<Traits> tra = traitsService.findById(id);

        if(tra.isPresent()){
            Traits trait = traitsService.updateTrait(tra.get(), updateTraitsDto);
            return ResponseEntity.ok(traitsDtoConverter.traitToGetTraitDto(trait));
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    @Operation(summary = "Borra una característica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha borrado correctamente",
                    content = { @Content(mediaType = "application/json")}),
            @ApiResponse(description = "No se encuentra la característica",
                    responseCode = "404",
                    content = @Content),
            @ApiResponse(description = "No se ha borrado la característica",
                    responseCode = "400",
                    content = @Content)
    })
    @DeleteMapping("trait/delete/{id}")
    public ResponseEntity<String> deleteTrait(@PathVariable Long id) throws IOException {
        Optional<Traits> trait = traitsService.findById(id);
        String name;

        if(trait.isPresent()){
            name = trait.get().getName();
            traitsService.deleteTrait(trait.get());
            if(!traitsService.existByName(name))
                return ResponseEntity.ok().build();
            else
                return ResponseEntity.badRequest().build();
        } else{
            return ResponseEntity.notFound().build();
        }
    }
}