package com.sa22.LMASpringData.services;

import com.sa22.LMASpringData.dtos.AuthorDto;
import com.sa22.LMASpringData.entities.AuthorEntity;

import com.sa22.LMASpringData.repositories.AuthorRepository;
import com.sa22.LMASpringData.services.serviceinterfaces.AuthorService;
import com.sa22.LMASpringData.validations.AuthorValidations;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.NonUniqueResultException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorValidations authorValidations;
    private final ModelMapper mapper;

    public AuthorServiceImpl(AuthorRepository authorRepository, ModelMapper mapper, AuthorValidations authorValidations) {
        this.authorValidations = authorValidations;
        this.authorRepository = authorRepository;
        this.mapper = mapper;
    }

    @Override
    public AuthorDto createAuthor(AuthorDto authorDto) throws NonUniqueResultException {

        if (!authorValidations.isAuthorFound(authorDto)) {

            // convert DTO to entity
            AuthorEntity author = mapToEntity(authorDto);
            AuthorEntity newAuthor = authorRepository.save(author);

            // convert entity to DTO
            return mapToDTO(newAuthor);
        } else throw new NonUniqueResultException();
    }

    @Override
    public AuthorDto getAuthorById(long id) {
        AuthorEntity authorEntity = authorRepository.findById(id).orElseThrow(NoSuchElementException::new);
        return mapToDTO(authorEntity);
    }


    @Override
    public void deleteAuthorById(long id) {
        // get post by id from the database
        if(authorValidations.isAuthorFound(id)){
            authorRepository.deleteById(id);
        }
        else throw new NoSuchElementException();
    }

    @Override
    public Optional<AuthorDto> getAuthorByName(String authorName) {
        AuthorEntity authorEntity = authorRepository.findByAuthorName(authorName).orElseThrow(NoSuchElementException::new);
        return Optional.ofNullable(mapToDTO(authorEntity));
    }

    @Override
    public List<AuthorDto> getAllAuthors() {
        List<AuthorEntity> authorEntityList = authorRepository.findAll();

        return authorEntityList.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    // convert Entity into DTO
    private AuthorDto mapToDTO(AuthorEntity authorEntity) {
        return mapper.map(authorEntity, AuthorDto.class);
    }

    // convert DTO to entity
    private AuthorEntity mapToEntity(AuthorDto authorDto) {
        return mapper.map(authorDto, AuthorEntity.class);
    }

}