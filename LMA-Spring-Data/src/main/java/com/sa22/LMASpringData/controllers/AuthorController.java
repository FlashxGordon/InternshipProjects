package com.sa22.LMASpringData.controllers;

import com.sa22.LMASpringData.dtos.AuthorDto;
import com.sa22.LMASpringData.services.serviceinterfaces.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/authors")
    public List<AuthorDto> getAllAuthors() throws NoSuchElementException {

        return ResponseEntity.ok(authorService.getAllAuthors()).getBody();
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/authors/{id}")
    public AuthorDto getAuthorById(@PathVariable long id) throws NoSuchElementException {

        return ResponseEntity.ok(authorService.getAuthorById(id)).getBody();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/authors")
    public ResponseEntity<String> createAuthor(@RequestBody AuthorDto authorDto) {

        AuthorDto savedAuthor = authorService.createAuthor(authorDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedAuthor.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/authors/{id}")
    public ResponseEntity<String> deleteAuthorById(@PathVariable long id) {

        authorService.deleteAuthorById(id);

        return new ResponseEntity<>("Author deleted successfully", HttpStatus.OK);
    }

}
