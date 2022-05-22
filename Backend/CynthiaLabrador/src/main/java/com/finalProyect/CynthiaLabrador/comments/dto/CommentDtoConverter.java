package com.finalProyect.CynthiaLabrador.comments.dto;

import com.finalProyect.CynthiaLabrador.comments.model.Comment;
import com.finalProyect.CynthiaLabrador.composition.dto.CompositionDtoConverter;
import com.finalProyect.CynthiaLabrador.users.dto.UserDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommentDtoConverter {
    @Autowired
    private CompositionDtoConverter compositionDtoConverter;

    @Autowired
    private UserDtoConverter userDtoConverter;

    public GetCommentDto CommentToGetCommentDto(Comment comment) {
      return GetCommentDto.builder()
                .id(comment.getId())
                .text(comment.getText())
                .composition(compositionDtoConverter.compositionToGetCompositionDto(comment.getComposition()))
                .author(userDtoConverter.userEntityToGetUserNameDto(comment.getAuthor()))
                .build();
    }

    public Comment CreateCommentToComment(CreateCommentDto createCommentDto) {
        return Comment.builder()
                .text(createCommentDto.getText())
                .createdAt(createCommentDto.getDate())
                .build();
    }

    public List<GetCommentDto> comentsToGetCommentDto(List<Comment> comments) {
        return comments.stream().map(this::CommentToGetCommentDto).collect(java.util.stream.Collectors.toList());
    }
}
