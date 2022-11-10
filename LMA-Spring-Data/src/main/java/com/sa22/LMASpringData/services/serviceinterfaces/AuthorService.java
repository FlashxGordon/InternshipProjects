package com.sa22.LMASpringData.services.serviceinterfaces;

import com.sa22.LMASpringData.dtos.AuthorDto;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    List<AuthorDto> getAllAuthors();

    AuthorDto createAuthor(AuthorDto authorDto);

    void deleteAuthorById(long id);

    Optional<AuthorDto> getAuthorByName(String authorName);


    AuthorDto getAuthorById(long id);
}
