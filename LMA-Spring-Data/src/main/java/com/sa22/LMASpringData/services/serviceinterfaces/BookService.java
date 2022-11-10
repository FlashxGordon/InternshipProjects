package com.sa22.LMASpringData.services.serviceinterfaces;

import com.sa22.LMASpringData.dtos.BookDto;
import com.sa22.LMASpringData.entities.BookEntity;

import java.util.List;
import java.util.Optional;

public interface BookService {

    List<BookEntity> getAllBooks();

    Optional<BookDto> findByBookById(long bookId);

    BookDto createBook(BookDto bookDto);
}
