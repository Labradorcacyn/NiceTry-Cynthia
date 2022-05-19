package com.finalProyect.CynthiaLabrador.traits.repository;

import com.finalProyect.CynthiaLabrador.traits.model.Traits;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TraitsRepository extends JpaRepository<Traits, Long> {

    Optional<Traits> findFirstByName(String name);
    boolean existsByName(String name);
}
