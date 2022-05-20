package com.finalProyect.CynthiaLabrador.comments.service;

import com.finalProyect.CynthiaLabrador.comments.dto.CommentDtoConverter;
import com.finalProyect.CynthiaLabrador.comments.dto.CreateCommentDto;
import com.finalProyect.CynthiaLabrador.comments.model.Comment;
import com.finalProyect.CynthiaLabrador.comments.repository.CommentRepository;
import com.finalProyect.CynthiaLabrador.composition.model.Composition;
import com.finalProyect.CynthiaLabrador.composition.repository.CompositionRepository;
import com.finalProyect.CynthiaLabrador.users.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CompositionRepository compositionRepository;
    private final CommentDtoConverter commentDtoConverter;

    public List<Comment> getAllComments(UUID id) {
        return commentRepository.findAllByCompositionId(id);
    }

    public Comment createComment(Composition composition, CreateCommentDto comment, UserEntity user) {
        Comment co = commentDtoConverter.CreateCommentToComment(comment);
        co.setComposition(composition);
        co.setAuthor(user);
        composition.getComments().add(co);
        commentRepository.save(co);
        compositionRepository.save(composition);

        return co;
    }

    public void deleteComment(UUID id, Composition composition) {
        composition.getComments().removeIf(comment -> comment.getId().equals(id));
    }

}
