package com.finalProyect.CynthiaLabrador.champions.repository;

import com.finalProyect.CynthiaLabrador.champions.model.Champion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface ChampionsRepository extends JpaRepository<Champion, UUID> {

    Optional<Champion> findFirstByName(String name);
    boolean existsByName(String name);
}
