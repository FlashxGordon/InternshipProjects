package com.sa22.LMA.library_management.book_management.book;

import com.sa22.LMA.library_management.book_management.book.book_services.BookCreatorService;
import com.sa22.LMA.library_management.book_management.book.book_services.BookFinderService;
import com.sa22.LMA.library_management.book_management.book.book_services.BookFinderByIdService;
import com.sa22.LMA.library_management.book_management.repositories.book_repository.BookCreator;
import com.sa22.LMA.library_management.book_management.repositories.book_repository.BookReader;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookFinderService, BookFinderByIdService, BookCreatorService {

    private final BookReader bookReader = new BookReader();
    private final BookCreator bookCreator = new BookCreator();

    @Override
    public boolean createBook(BookDTO bookDTO) {
        return bookCreator.createBook(bookDTO);
    }
    @Override
    public List<Book> findAllBooks() {
        return bookReader.findAllBooks();
    }

    @Override
    public Book findBookById(int bookId) {
        return bookReader.findBookById(bookId);
    }
}
