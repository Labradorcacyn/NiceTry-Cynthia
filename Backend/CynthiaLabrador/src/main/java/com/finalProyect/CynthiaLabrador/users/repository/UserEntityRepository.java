package com.finalProyect.CynthiaLabrador.users.repository;

import com.finalProyect.CynthiaLabrador.users.model.UserEntity;
import com.finalProyect.CynthiaLabrador.users.model.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserEntityRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findFirstByEmail(String nombre);

    Optional<List<UserEntity>> findByUserRoles(UserRoles userRoles);

    Optional<UserEntity> findById(UUID uuid);

    UserEntity findByNick(String nick);

    UserEntity findByFollowersContains(UserEntity user);

    boolean existsByNick(String nick);
}
