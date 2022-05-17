package com.finalProyect.CynthiaLabrador.composition.repository;
import com.finalProyect.CynthiaLabrador.composition.model.Composition;
import com.finalProyect.CynthiaLabrador.users.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CompositionRepository extends JpaRepository<Composition, UUID> {

    Optional<List<Composition>> findAllByAuthor(UserEntity author);


}
