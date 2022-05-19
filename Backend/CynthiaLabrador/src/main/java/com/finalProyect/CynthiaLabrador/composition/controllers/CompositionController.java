package com.finalProyect.CynthiaLabrador.composition.controllers;

import com.finalProyect.CynthiaLabrador.champions.model.Champion;
import com.finalProyect.CynthiaLabrador.champions.services.ChampionsService;
import com.finalProyect.CynthiaLabrador.composition.dto.CompositionDtoConverter;
import com.finalProyect.CynthiaLabrador.composition.dto.CreateCompositionDto;
import com.finalProyect.CynthiaLabrador.composition.dto.GetCompositionDto;
import com.finalProyect.CynthiaLabrador.composition.model.Composition;
import com.finalProyect.CynthiaLabrador.composition.services.CompositionService;
import com.finalProyect.CynthiaLabrador.users.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
@CrossOrigin(origins = "http://localhost:4200")
public class CompositionController {

    private final CompositionService compositionService;
    private final CompositionDtoConverter compositionDtoConverter;
    private final ChampionsService championsService;

    @GetMapping("/composition/author/{nick}")
    public ResponseEntity<List<GetCompositionDto>> getComposition(@PathVariable String nick) {
       List<Composition> compositionAuth = compositionService.getAllCompositionByUser(nick);

       if(compositionAuth.isEmpty()) {
           return ResponseEntity.notFound().build();
       }
       else {
           return ResponseEntity.ok(compositionDtoConverter.ListCompositionToListGetCompositionDto(compositionAuth));
       }
    }

    @GetMapping("/composition")
    public ResponseEntity<List<GetCompositionDto>> getAllComposition() {
        List<Composition> composition = compositionService.getAllComposition();

        if (composition.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(compositionDtoConverter.ListCompositionToListGetCompositionDto(composition));
    }

    @GetMapping("/composition/id/{id}")
    public ResponseEntity<GetCompositionDto> getCompositionById(@PathVariable UUID id) {
        Composition composition = compositionService.getCompositionById(id);

        if (composition == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(compositionDtoConverter.CompositionToGetCompositionDto(composition));
    }

    @PostMapping("/composition")
    public ResponseEntity<GetCompositionDto> createComposition(@RequestPart ("body") CreateCompositionDto createCompositionDto, @AuthenticationPrincipal UserEntity userEntity) {
        Composition composition = compositionService.createComposition(createCompositionDto, userEntity);

        return ResponseEntity.ok(compositionDtoConverter.CompositionToGetCompositionDto(composition));
    }

    @PutMapping("/composition/{id}")
    public ResponseEntity<GetCompositionDto> updateComposition(@PathVariable UUID id, @RequestPart ("body") CreateCompositionDto createCompositionDto, @AuthenticationPrincipal UserEntity userEntity) {
        Composition c = compositionDtoConverter.CompositionDtoToGetComposition(createCompositionDto);
        List<Champion> champions = new ArrayList<>();
        createCompositionDto.getChampions().forEach(champion -> {
           Champion ch = championsService.findFirstByName(champion);
            if(ch != null) {
                champions.add(ch);
            }
        });
        c.setChampions(champions);
        Composition composition = compositionService.updateComposition(c, id, userEntity);

        if (composition == null) {
            return ResponseEntity.badRequest().build();
        }else
        return ResponseEntity.ok(compositionDtoConverter.CompositionToGetCompositionDto(composition));
    }

    @DeleteMapping("/composition/{id}")
    public ResponseEntity<?> deleteComposition(@PathVariable UUID id, @AuthenticationPrincipal UserEntity userEntity) {
        Composition c = compositionService.getCompositionById(id);
        if (c == null) {
            return ResponseEntity.notFound().build();
        }
        if (!c.getAuthor().getNick().equals(userEntity.getNick())){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }else{
            compositionService.deleteComposition(id, userEntity);
            return ResponseEntity.ok("Composition deleted");
        }
    }

    @PutMapping("/composition/{id}/vote")
    public ResponseEntity<?> addVote(@PathVariable UUID id, @AuthenticationPrincipal UserEntity userEntity) {
        Composition composition = compositionService.getCompositionById(id);

        if(composition.getVotes().contains(userEntity.getName())){
            return ResponseEntity.badRequest().body("You already voted");
        }

        if (composition == null) {
            return ResponseEntity.notFound().build();
        }
        compositionService.addVote(id, userEntity);
        return ResponseEntity.ok("Vote added");
    }

    @DeleteMapping("/composition/{id}/vote")
    public ResponseEntity<?> removeVote(@PathVariable UUID id, @AuthenticationPrincipal UserEntity userEntity) {
        Composition composition = compositionService.getCompositionById(id);

        if(!composition.getVotes().contains(userEntity.getName())){
            return ResponseEntity.badRequest().body("You haven't voted");
        }

        if (composition == null) {
            return ResponseEntity.notFound().build();
        }
        compositionService.removeVote(id, userEntity);
        return ResponseEntity.ok("Vote removed");
    }
}
