package com.sa22.LMASpringData.services;

import com.sa22.LMASpringData.dtos.BookDto;
import com.sa22.LMASpringData.entities.BookEntity;
import com.sa22.LMASpringData.repositories.AuthorRepository;
import com.sa22.LMASpringData.repositories.BookRepository;
import com.sa22.LMASpringData.services.serviceinterfaces.BookService;
import com.sa22.LMASpringData.validations.AuthorValidations;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    private final AuthorValidations authorValidations;
    private final ModelMapper mapper;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, AuthorValidations authorValidations, ModelMapper mapper) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.authorValidations = authorValidations;
        this.mapper = mapper;
    }

    @Override
    public List<BookEntity> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Optional<BookDto> findByBookById(long bookId) {
        BookEntity bookEntity = bookRepository.findById(bookId).orElseThrow(NoSuchElementException::new);

        return Optional.ofNullable(mapToDTO(bookEntity));
    }

    @Override
    public BookDto createBook(BookDto bookDto) throws NoSuchElementException {
        if(authorValidations.isAuthorFound(bookDto)) {

            BookEntity bookEntity = mapToEntity(bookDto);
            BookEntity newBookEntity = bookRepository.save(bookEntity);

            return mapToDTO(newBookEntity);
        } else throw new NoSuchElementException();

    }

    private BookDto mapToDTO(BookEntity bookEntity) {
        return mapper.map(bookEntity, BookDto.class);
    }

    // convert DTO to entity
    private BookEntity mapToEntity(BookDto bookDto) {
        return mapper.map(bookDto, BookEntity.class);
    }











//    @Override
//    public BookEntity createBook(BookDto bookDto) throws EmptyInputException {
//        List<AuthorEntity> authorEntityList = authorRepository.findAll();
//        if (!authorEntityList.toString().contains(bookDto.getAuthorName())) {
//
//            throw new NoSuchElementException("No authors with that name found in DB.");
//        }
//        if (bookDto.getBookName().isEmpty() || bookDto.getBookName().isBlank()) {
//
//            throw new EmptyInputException("Missing book name input.");
//        }
//        if (bookDto.getAuthorName().isBlank() || bookDto.getAuthorName().isEmpty()) {
//
//            throw new EmptyInputException("Missing author name input.");
//        }
//        BookEntity bookEntity = new BookEntity(bookDto.getBookName(),bookDto.getDateOfPublishing());
//        return bookRepository.saveAndFlush(bookEntity);
//    }


}
