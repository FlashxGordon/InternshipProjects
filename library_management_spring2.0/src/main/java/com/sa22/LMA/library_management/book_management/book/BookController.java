package com.sa22.LMA.library_management.book_management.book;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookServiceImpl bookServiceImpl;

    public BookController(BookServiceImpl bookServiceImpl) {
        this.bookServiceImpl = bookServiceImpl;
    }

    @GetMapping("/books")
    public List<Book> findAllBooks() {
        return bookServiceImpl.findAllBooks();
    }

    @GetMapping("/book/{id}")
    public Book findBookById(@PathVariable int id) {
        return bookServiceImpl.findBookById(id);
    }

    @PostMapping("/new-book")
    public String insertBook(@RequestBody BookDTO bookDTO) {
        return bookServiceImpl.createBook(bookDTO) + " number of rows inserted into the TABLE author.";
    }

}
