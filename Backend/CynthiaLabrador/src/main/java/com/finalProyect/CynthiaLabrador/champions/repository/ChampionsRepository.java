package com.finalProyect.CynthiaLabrador.champions.repository;

import com.finalProyect.CynthiaLabrador.champions.model.Champion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface ChampionsRepository extends JpaRepository<Champion, UUID> {

    @Query("SELECT c FROM Champions c WHERE c.name = ?1")
    Optional<Champion> findByName(String name);

    boolean existsByName(String name);
}
