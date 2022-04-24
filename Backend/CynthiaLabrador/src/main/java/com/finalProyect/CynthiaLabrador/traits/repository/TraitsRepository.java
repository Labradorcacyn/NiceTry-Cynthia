package com.finalProyect.CynthiaLabrador.traits.repository;

import com.finalProyect.CynthiaLabrador.traits.model.Traits;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TraitsRepository extends JpaRepository<Traits, UUID> {

    Optional<Traits> findFirstByName(String name);
    boolean existsByName(String name);
}
