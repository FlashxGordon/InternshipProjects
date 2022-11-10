package com.sa22.LMASpringData.security.repositories;

import com.sa22.LMASpringData.security.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findUserByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsById(long id);

}
