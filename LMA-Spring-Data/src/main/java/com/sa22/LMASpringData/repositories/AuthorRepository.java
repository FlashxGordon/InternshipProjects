package com.sa22.LMASpringData.repositories;

import com.sa22.LMASpringData.dtos.AuthorDto;
import com.sa22.LMASpringData.entities.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<AuthorEntity, Long> {

    Optional<AuthorEntity> findByAuthorName(String authorName);


}
