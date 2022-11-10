package com.sa22.LMASpringData.validations;

import com.sa22.LMASpringData.dtos.AuthorDto;
import com.sa22.LMASpringData.dtos.BookDto;
import com.sa22.LMASpringData.entities.AuthorEntity;
import com.sa22.LMASpringData.repositories.AuthorRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class AuthorValidations {

    private final AuthorRepository authorRepository;

    public AuthorValidations(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public boolean isAuthorFound(BookDto bookDto) {

        Optional<Optional<AuthorEntity>> authorEntity = Optional.ofNullable(authorRepository.findByAuthorName(bookDto.getAuthorName()));

        return authorEntity.get().isPresent();
    }

    public boolean isAuthorFound(AuthorDto authorDto) {

        Optional<Optional<AuthorEntity>> authorEntity = Optional.ofNullable(authorRepository.findByAuthorName(authorDto.getAuthorName()));
        //returning entity with .get() and .isPresent() because .isPresent() alone will always return true
        return authorEntity.get().isPresent();
    }

    public boolean isAuthorFound(long authorId) {

        Optional<Optional<AuthorEntity>> authorEntity = Optional.of(authorRepository.findById(authorId));
        //returning entity with .get() and .isPresent() because .isPresent() alone will always return true
        return authorEntity.get().isPresent();
    }
}
