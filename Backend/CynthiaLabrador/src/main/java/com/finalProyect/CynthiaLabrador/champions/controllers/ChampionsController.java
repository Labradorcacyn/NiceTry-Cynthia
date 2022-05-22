package com.finalProyect.CynthiaLabrador.champions.controllers;

import com.finalProyect.CynthiaLabrador.champions.dto.ChampionDtoConverter;
import com.finalProyect.CynthiaLabrador.champions.dto.CreateChampionDto;
import com.finalProyect.CynthiaLabrador.champions.dto.GetChampionDto;
import com.finalProyect.CynthiaLabrador.champions.model.Champion;
import com.finalProyect.CynthiaLabrador.champions.services.ChampionsService;
import com.finalProyect.CynthiaLabrador.errors.excepciones.ChampionNotFoundException;
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

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Controlador de los campeones")
public class ChampionsController {

    private final ChampionsService championsService;
    private final ChampionDtoConverter championDtoConverter;

    @Operation(summary = "Crea un campeón" , description = "Crea un campeón" , tags = {"Campeones"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha creado correctamente",
                    content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400",
                    description = "El nombre del campeón ya existe",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "No se ha podido crear el campeón",
                    content = @Content)
    })
    @PostMapping("champion/create")
    public ResponseEntity<GetChampionDto> createChampion(@RequestPart("body") CreateChampionDto createChampionDto,
                                                         @RequestPart("file") MultipartFile file) throws Exception {

        Champion champion = championDtoConverter.championDtoToGetChampion(createChampionDto);

        if (championsService.existByName(champion.getName()))
            return ResponseEntity.badRequest().build();

        Champion saveChampion = championsService.saveChampion(createChampionDto, file);
        if (saveChampion == null)
            return ResponseEntity.badRequest().build();
        else
            return ResponseEntity.status(HttpStatus.CREATED).body(championDtoConverter.championToGetChampionDto(saveChampion));
    }

    @Operation(summary = "Obtiene un campeón" , description = "Obtiene un campeón" , tags = {"Campeones"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha obtenido correctamente",
                    content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado el campeón",
                    content = @Content)
    })
    @GetMapping("champion/{id}")
    public ResponseEntity<GetChampionDto> getChampion(@PathVariable UUID id) throws ChampionNotFoundException {
        Champion champion = championsService.getChampionById(id);
        if(champion == null)
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(championDtoConverter.championToGetChampionDto(champion));
    }

    @Operation(summary = "Obtiene un campeón" , description = "Obtiene un campeón" , tags = {"Campeones"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha obtenido correctamente",
                    content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado el campeón",
                    content = @Content)
    })
    @GetMapping("champion/name/{name}")
    public ResponseEntity<GetChampionDto> getChampionByName(@PathVariable String name) throws ChampionNotFoundException {
        String[] nameArray = name.split("");
        for (int i = 0; i < nameArray.length; i++) {
            nameArray[i] = nameArray[i].toLowerCase();
        }
        nameArray[0] = nameArray[0].toUpperCase();
        name = String.join("", nameArray);
        Champion champion = championsService.findByName(name);
        if(champion == null)
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(championDtoConverter.championToGetChampionDto(champion));
    }
    @Operation(summary = "Obtiene todos los campeones" , description = "Obtiene todos los campeones" , tags = {"Campeones"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han obtenido correctamente",
                    content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado los campeones",
                    content = @Content)
    })
    @GetMapping("champions")
    public ResponseEntity<List<GetChampionDto>> getAllChampions() {
        List<Champion> champions = championsService.getAllChampions();
        if(champions == null)
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(championDtoConverter.championsToGetChampionDto(champions));
    }

    @Operation(summary = "Actualiza un campeón" , description = "Actualiza un campeón" , tags = {"Campeones"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha actulizado correctamente",
                    content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado el campeón",
                    content = @Content)
    })
    @PutMapping("champion/update/{id}")
    public ResponseEntity<GetChampionDto> updateChampion(@PathVariable UUID id,
                                                         @RequestPart("body") CreateChampionDto updateChampionDto,
                                                         @RequestPart("file") MultipartFile file) throws Exception {
        Optional<Champion> ch = championsService.findById(id);

        if(ch.isPresent()){
            Champion champion = championsService.updateChampion(ch.get(), updateChampionDto, file);
            return ResponseEntity.ok(championDtoConverter.championToGetChampionDto(champion));
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Borra un campeón" , description = "Borra un campeón" , tags = {"Campeones"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha borrado correctamente",
                    content = { @Content(mediaType = "application/json")}),
            @ApiResponse(description = "No se encuentra el campeón",
                    responseCode = "404",
                    content = @Content)
    })
    @DeleteMapping("champion/delete/{id}")
    public ResponseEntity<String> deleteChampion(@PathVariable UUID id) throws ChampionNotFoundException, IOException {
        Optional<Champion> champion = championsService.findById(id);

        if(champion.isPresent()){
            championsService.deleteChampion(champion.get());
            return ResponseEntity.ok().build();

        }else{
            return ResponseEntity.notFound().build();
        }
    }
}