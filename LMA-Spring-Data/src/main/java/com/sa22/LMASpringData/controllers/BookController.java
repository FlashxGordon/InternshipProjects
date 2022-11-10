package com.sa22.LMASpringData.controllers;

import com.sa22.LMASpringData.dtos.BookDto;
import com.sa22.LMASpringData.entities.BookEntity;
import com.sa22.LMASpringData.services.serviceinterfaces.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class BookController {


    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/books")
    public List<BookEntity> getAllBooks() throws NoSuchElementException {
        List<BookEntity> bookEntityList = new ArrayList<>();
        if (bookEntityList.toString().isEmpty()) {
            ResponseEntity.status(HttpStatus.NOT_FOUND);
            throw new NoSuchElementException("No books found in DB");
        }
        return ResponseEntity.ok(bookService.getAllBooks()).getBody();
    }
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/books/{id}")
    public Optional<BookDto> findByBookById(@PathVariable long id) throws NoSuchElementException {
        return ResponseEntity.ok(bookService.findByBookById(id)).getBody();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/books")
    public ResponseEntity<BookDto> createBook(@RequestBody BookDto bookDto) {


        BookDto savedBook = bookService.createBook(bookDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedBook.getBookId()).toUri();


        return ResponseEntity.created(location).build();
    }

}


