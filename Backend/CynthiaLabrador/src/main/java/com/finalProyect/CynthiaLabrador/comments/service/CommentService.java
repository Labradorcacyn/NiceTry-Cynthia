package com.finalProyect.CynthiaLabrador.comments.service;

import com.finalProyect.CynthiaLabrador.comments.dto.CommentDtoConverter;
import com.finalProyect.CynthiaLabrador.comments.dto.CreateCommentDto;
import com.finalProyect.CynthiaLabrador.comments.model.Comment;
import com.finalProyect.CynthiaLabrador.comments.repository.CommentRepository;
import com.finalProyect.CynthiaLabrador.composition.model.Composition;
import com.finalProyect.CynthiaLabrador.composition.repository.CompositionRepository;
import com.finalProyect.CynthiaLabrador.users.model.UserEntity;
import com.finalProyect.CynthiaLabrador.users.model.UserRoles;
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

    public void deleteComment(UUID id, Composition composition, UserEntity user) {
        if (user.getId().equals(composition.getAuthor().getId()) || user.getUserRoles() == UserRoles.ADMIN) {
            composition.getComments().forEach(c -> {
                if (c.getId().equals(id)) {
                    composition.getComments().remove(c);
                    commentRepository.delete(c);
                }
            });
        }
    }
}
