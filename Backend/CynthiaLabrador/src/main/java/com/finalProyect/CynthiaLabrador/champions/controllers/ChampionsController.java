package com.finalProyect.CynthiaLabrador.champions.controllers;

import com.finalProyect.CynthiaLabrador.champions.dto.ChampionDtoConverter;
import com.finalProyect.CynthiaLabrador.champions.dto.CreateChampionDto;
import com.finalProyect.CynthiaLabrador.champions.dto.GetChampionDto;
import com.finalProyect.CynthiaLabrador.champions.model.Champion;
import com.finalProyect.CynthiaLabrador.champions.services.ChampionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
@CrossOrigin(origins = "http://localhost:4200")
public class ChampionsController {

    private ChampionsService championsService;
    private ChampionDtoConverter championDtoConverter;

    @PostMapping("champion/create")
    public ResponseEntity<GetChampionDto> registerChampion (@RequestPart("body") CreateChampionDto createChampionDto, @RequestPart("file") MultipartFile file) throws Exception {

        Champion champion = championDtoConverter.ChampionDtoToGetChampion(createChampionDto);

        if(championsService.existByName(champion.getName()))
            return ResponseEntity.badRequest().build();

        Champion saveChampion = championsService.saveChampion(createChampionDto, file);
        if (saveChampion == null)
            return ResponseEntity.badRequest().build();
        else
            return ResponseEntity.status(HttpStatus.CREATED).body(championDtoConverter.ChampionToGetChampionDto(saveChampion));
    }
}
