package com.finalProyect.CynthiaLabrador.champions.controllers;

import com.finalProyect.CynthiaLabrador.champions.dto.ChampionDtoConverter;
import com.finalProyect.CynthiaLabrador.champions.dto.CreateChampionDto;
import com.finalProyect.CynthiaLabrador.champions.dto.GetChampionDto;
import com.finalProyect.CynthiaLabrador.champions.model.Champion;
import com.finalProyect.CynthiaLabrador.champions.services.ChampionsService;
import com.finalProyect.CynthiaLabrador.errors.excepciones.ChampionNotFoundException;
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
public class ChampionsController {

    private final ChampionsService championsService;
    private final ChampionDtoConverter championDtoConverter;

    @PostMapping("champion/create")
    public ResponseEntity<GetChampionDto> createChampion(@RequestPart("body") CreateChampionDto createChampionDto,
                                                         @RequestPart("file") MultipartFile file) throws Exception {

        Champion champion = championDtoConverter.ChampionDtoToGetChampion(createChampionDto);

        if (championsService.existByName(champion.getName()))
            return ResponseEntity.badRequest().build();

        Champion saveChampion = championsService.saveChampion(createChampionDto, file);
        if (saveChampion == null)
            return ResponseEntity.badRequest().build();
        else
            return ResponseEntity.status(HttpStatus.CREATED).body(championDtoConverter.ChampionToGetChampionDto(saveChampion));
    }

    @GetMapping("champion/{id}")
    public ResponseEntity<GetChampionDto> getChampion(@PathVariable UUID id) throws ChampionNotFoundException {
        Champion champion = championsService.getChampionById(id);
        return ResponseEntity.ok(championDtoConverter.ChampionToGetChampionDto(champion));
    }

    @GetMapping("champion/name/{name}")
    public ResponseEntity<GetChampionDto> getChampionByName(@PathVariable String name) throws ChampionNotFoundException {
        String[] nameArray = name.split("");
        for (int i = 0; i < nameArray.length; i++) {
            nameArray[i] = nameArray[i].toLowerCase();
        }
        nameArray[0] = nameArray[0].toUpperCase();
        name = String.join("", nameArray);
        Champion champion = championsService.findFirstByName(name);
        return ResponseEntity.ok(championDtoConverter.ChampionToGetChampionDto(champion));
    }

    @GetMapping("champions")
    public ResponseEntity<List<GetChampionDto>> getAllChampions() {
        List<Champion> champions = championsService.getAllChampions();
        return ResponseEntity.ok(championDtoConverter.ChampionsToGetChampionDto(champions));
    }

    @PutMapping("champion/update/{id}")
    public ResponseEntity<GetChampionDto> updateChampion(@PathVariable UUID id,
                                                         @RequestPart("body") CreateChampionDto updateChampionDto,
                                                         @RequestPart("file") MultipartFile file) throws Exception {
        Optional<Champion> ch = championsService.findById(id);

        if(ch.isPresent()){
            Champion champion = championsService.updateChampion(ch.get(), updateChampionDto, file);
            return ResponseEntity.ok(championDtoConverter.ChampionToGetChampionDto(champion));
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("champion/delete/{id}")
    public ResponseEntity<String> deleteChampion(@PathVariable UUID id) throws ChampionNotFoundException, IOException {
        Optional<Champion> champion = championsService.findById(id);

        if(champion.isPresent()){
            championsService.deleteChampion(champion.get());
            return ResponseEntity.ok("Champion deleted");

        }else{
            return ResponseEntity.notFound().build();
        }
    }
}