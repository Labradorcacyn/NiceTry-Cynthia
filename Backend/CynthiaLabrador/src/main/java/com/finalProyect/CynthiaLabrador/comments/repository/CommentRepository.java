package com.finalProyect.CynthiaLabrador.comments.repository;

import com.finalProyect.CynthiaLabrador.comments.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CommentRepository extends JpaRepository <Comment, UUID> {

    List<Comment> findAllByCompositionId(UUID postId);
}
