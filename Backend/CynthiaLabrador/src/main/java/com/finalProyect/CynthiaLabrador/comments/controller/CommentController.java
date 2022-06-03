package com.finalProyect.CynthiaLabrador.comments.controller;

import com.finalProyect.CynthiaLabrador.comments.dto.CommentDtoConverter;
import com.finalProyect.CynthiaLabrador.comments.dto.CreateCommentDto;
import com.finalProyect.CynthiaLabrador.comments.dto.GetCommentDto;
import com.finalProyect.CynthiaLabrador.comments.model.Comment;
import com.finalProyect.CynthiaLabrador.comments.service.CommentService;
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

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Controlador de los comentarios")
public class CommentController {

    private final CommentService commentService;
    private final CommentDtoConverter commentDtoConverter;
    private final CompositionService compositionService;

    @Operation(summary = "Devuelve todos los comentarios de una composición", description = "Devuelve todos los comentarios de una composición", tags = {"Comentarios"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Comentarios encontrados",
                    content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = "No se encuentran comentarios",
                    content = @Content)
    })
    @GetMapping("{id}/comments")
    public ResponseEntity<List<GetCommentDto>> getComments(@PathVariable UUID id) {
        List<Comment> comments = commentService.getAllComments(id);
        if (comments.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else
            return ResponseEntity.ok(commentDtoConverter.comentsToGetCommentDto(comments));
    }

    @Operation(summary = "Crea un comentario", description = "Crea un comentario", tags = {"Comentarios"})
    @ApiResponse(responseCode = "201", description = "Se ha creado correctamente", content = { @Content(mediaType = "application/json")})
    @PostMapping("{id}/comment")
    public ResponseEntity<GetCommentDto> createComment(@PathVariable UUID id, @RequestPart("body") CreateCommentDto comment, @AuthenticationPrincipal UserEntity userEntity) {
        Composition composition = compositionService.getById(id);
        Comment com = commentService.createComment(composition, comment, userEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(commentDtoConverter.CommentToGetCommentDto(com));
    }

    @Operation(summary = "Borrar tu comentario", description = "Borrar tu comentario", tags = {"Comentarios"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha borrado correctamente",
                    content = { @Content(mediaType = "application/json")}),
            @ApiResponse(description = "No se encuentra el comentario",
                    responseCode = "404",
                    content = @Content)
    })
    @DeleteMapping("{id}/comments/{idComment}")
    public ResponseEntity<?> deleteComment(@PathVariable UUID id, @PathVariable UUID idComment, @AuthenticationPrincipal UserEntity userEntity) {
        Optional<Composition> composition = compositionService.findById(id);
        if (composition.isPresent()) {
            commentService.deleteComment(idComment, composition.get(), userEntity);
            composition.get().getComments().stream().map(c -> {
                if (!c.getId().equals(idComment)) {
                    return ResponseEntity.ok().build();
                } else return ResponseEntity.badRequest().build();
            });
        }
        return ResponseEntity.notFound().build();
    }
}
