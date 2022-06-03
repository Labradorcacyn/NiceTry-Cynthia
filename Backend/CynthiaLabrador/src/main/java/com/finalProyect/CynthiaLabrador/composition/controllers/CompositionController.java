package com.finalProyect.CynthiaLabrador.composition.controllers;

import com.finalProyect.CynthiaLabrador.champions.model.Champion;
import com.finalProyect.CynthiaLabrador.champions.services.ChampionsService;
import com.finalProyect.CynthiaLabrador.composition.dto.CompositionDtoConverter;
import com.finalProyect.CynthiaLabrador.composition.dto.CreateCompositionDto;
import com.finalProyect.CynthiaLabrador.composition.dto.GetCompositionDto;
import com.finalProyect.CynthiaLabrador.composition.model.Composition;
import com.finalProyect.CynthiaLabrador.composition.services.CompositionService;
import com.finalProyect.CynthiaLabrador.users.model.UserEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Controlador de las composiciones")
public class CompositionController {

    private final CompositionService compositionService;
    private final CompositionDtoConverter compositionDtoConverter;
    private final ChampionsService championsService;

    @Operation(summary = "Obtiene las composiciones de un usuario", description = "Obtiene las composiciones de un usuario", tags = {"Composiciones"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Composiciones obtenidas",
                    content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = "No se encuentra la lista de composiciones",
                    content = @Content)
    })
    @GetMapping("/composition/author/{nick}")
    public ResponseEntity<List<GetCompositionDto>> getComposition(@PathVariable String nick) {
       List<Composition> compositionAuth = compositionService.getAllCompositionByUser(nick);

       if(compositionAuth.isEmpty()) {
           return ResponseEntity.notFound().build();
       }
       else {
           return ResponseEntity.ok(compositionDtoConverter.listCompositionToListGetCompositionDto(compositionAuth));
       }
    }
    @Operation(summary = "Obtiene las composiciones", description = "Obtiene las composiciones", tags = {"Composiciones"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Composiciones obtenidas",
                    content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = "No se encuentra la lista de composiciones",
                    content = @Content)
    })
    @GetMapping("/composition")
    public ResponseEntity<List<GetCompositionDto>> getAllComposition() {
        List<Composition> composition = compositionService.getAllComposition();

        if (composition.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(compositionDtoConverter.listCompositionToListGetCompositionDto(composition));
    }

    @Operation(summary = "Obtiene una composicion por id", description = "Obtiene una composicion por id", tags = {"Composiciones"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Composiciones obtenidas",
                    content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = "No se encuentra la lista de composiciones",
                    content = @Content)
    })
    @GetMapping("/composition/id/{id}")
    public ResponseEntity<GetCompositionDto> getCompositionById(@PathVariable UUID id) {
        Composition composition = compositionService.getCompositionById(id);

        if (composition == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(compositionDtoConverter.compositionToGetCompositionDto(composition));
    }

    @Operation(summary = "Crea una nueva composicion", description = "Crea una nueva composicion", tags = {"Composiciones"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha creado correctamente",
                    content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha podido crear la composicion",
                    content = @Content)
    })
    @PostMapping("/composition")
    public ResponseEntity<GetCompositionDto> createComposition(@RequestBody CreateCompositionDto createCompositionDto, @AuthenticationPrincipal UserEntity userEntity) {
        Composition composition = compositionService.createComposition(createCompositionDto, userEntity);

        if(compositionService.findById(composition.getId()).isEmpty()) {
            return ResponseEntity.badRequest().build();
        }else
            return ResponseEntity.status(HttpStatus.CREATED).body(compositionDtoConverter.compositionToGetCompositionDto(composition));
    }

    @Operation(summary = "Actualizar una nueva composicion", description = "Actualizar una nueva composicion", tags = {"Composiciones"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha actualizado correctamente",
                    content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha podido actualizar la composicion",
                    content = @Content)
    })
    @PutMapping("/composition/{id}")
    public ResponseEntity<GetCompositionDto> updateComposition(@PathVariable UUID id, @RequestBody CreateCompositionDto createCompositionDto, @AuthenticationPrincipal UserEntity userEntity) {
        Composition c = compositionDtoConverter.compositionDtoToGetComposition(createCompositionDto);
        List<Champion> champions = new ArrayList<>();
        createCompositionDto.getChampions().forEach(champion -> {
           Champion ch = championsService.findByName(champion);
            if(ch != null) {
                champions.add(ch);
            }
        });
        c.setChampions(champions);
        Composition composition = compositionService.updateComposition(c, id, userEntity);

        if (composition == null) {
            return ResponseEntity.badRequest().build();
        }else
            return ResponseEntity.ok(compositionDtoConverter.compositionToGetCompositionDto(composition));
    }

    @Operation(summary = "Borra una composicion", description = "Borra una composicion", tags = {"Composiciones"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha borrado correctamente",
                    content = { @Content(mediaType = "application/json")}),
            @ApiResponse(description = "No se encuentra la Inmobiliaria",
                    responseCode = "404",
                    content = @Content)
    })
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
            return ResponseEntity.ok().build();
        }
    }

    @Operation(summary = "Votar una composicion", description = "Votar una composicion", tags = {"Composiciones"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Has realizado tu voto correctamente",
                    content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500",
                    description = "No se ha podido relizar el voto",
                    content = @Content)
    })
    @PutMapping("/composition/{id}/vote")
    public ResponseEntity<?> addVote(@PathVariable UUID id, @AuthenticationPrincipal UserEntity userEntity) {
        Composition composition = compositionService.getCompositionById(id);

        if(composition.getVotes().contains(userEntity.getNick())){
            return ResponseEntity.badRequest().body("You already voted");
        }

        if (composition == null) {
            return ResponseEntity.notFound().build();
        }
        compositionService.addVote(id, userEntity);
        return ResponseEntity.ok("Vote added");
    }

    @Operation(summary = "Borrar tu voto", description = "Borrar tu voto", tags = {"Composiciones"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha borrado correctamente",
                    content = { @Content(mediaType = "application/json")}),
            @ApiResponse(description = "No se encuentra tu voto",
                    responseCode = "404",
                    content = @Content)
    })
    @DeleteMapping("/composition/{id}/vote")
    public ResponseEntity<?> removeVote(@PathVariable UUID id, @AuthenticationPrincipal UserEntity userEntity) {
        Composition composition = compositionService.getCompositionById(id);

        if(!composition.getVotes().contains(userEntity.getNick())){
            return ResponseEntity.badRequest().build();
        }

        if (composition == null) {
            return ResponseEntity.notFound().build();
        }
        compositionService.removeVote(id, userEntity);
        return ResponseEntity.ok().build();
    }
}
