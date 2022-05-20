package com.finalProyect.CynthiaLabrador.comments.controller;

import com.finalProyect.CynthiaLabrador.comments.dto.CommentDtoConverter;
import com.finalProyect.CynthiaLabrador.comments.dto.CreateCommentDto;
import com.finalProyect.CynthiaLabrador.comments.dto.GetCommentDto;
import com.finalProyect.CynthiaLabrador.comments.model.Comment;
import com.finalProyect.CynthiaLabrador.comments.service.CommentService;
import com.finalProyect.CynthiaLabrador.composition.model.Composition;
import com.finalProyect.CynthiaLabrador.composition.services.CompositionService;
import com.finalProyect.CynthiaLabrador.users.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
@CrossOrigin(origins = "http://localhost:4200")
public class CommentController {

    private final CommentService commentService;
    private final CommentDtoConverter commentDtoConverter;
    private final CompositionService compositionService;

    @GetMapping("{id}/comments")
    public ResponseEntity<List<GetCommentDto>> getComments(@PathVariable UUID id) {
        List<Comment> comments = commentService.getAllComments(id);
        return ResponseEntity.ok(commentDtoConverter.comentsToGetCommentDto(comments));
    }

    @PostMapping("{id}/comment")
    public ResponseEntity<GetCommentDto> createComment(@PathVariable UUID id, @RequestPart("body") CreateCommentDto comment, @AuthenticationPrincipal UserEntity userEntity) {
        Composition composition = compositionService.getById(id);
        Comment com = commentService.createComment(composition, comment, userEntity);
        return ResponseEntity.ok(commentDtoConverter.CommentToGetCommentDto(com));
    }

    @DeleteMapping("{id}/comments/{idComment}")
    public ResponseEntity<?> deleteComment(@PathVariable UUID id, @PathVariable UUID idComment) {
        Composition composition = compositionService.getById(id);
        commentService.deleteComment(idComment, composition);

        composition.getComments().stream().map(comment -> {
            if (!comment.getId().equals(idComment)) {
                return ResponseEntity.ok().body("Comment deleted");
            }else {
                return ResponseEntity.badRequest().body("Comment not deleted");
            }
        });
        return ResponseEntity.badRequest().build();
    }

}
