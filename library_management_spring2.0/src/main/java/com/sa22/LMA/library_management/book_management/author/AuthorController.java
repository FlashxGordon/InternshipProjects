package com.sa22.LMA.library_management.book_management.author;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorServiceImpl authorServiceImpl;

    AuthorController(AuthorServiceImpl authorServiceImpl) {
        this.authorServiceImpl = authorServiceImpl;
    }

    @GetMapping("/all-authors")
    public List<Author> authorsGetAll() {
        return authorServiceImpl.findAllAuthors();
    }

    @GetMapping("/author/{id}")
    public Author getAuthorById(@PathVariable int id) {
        return authorServiceImpl.findAuthorById(id);
    }

    @PostMapping("/new-author")
    public String insertAuthor(@RequestBody AuthorDTO authorDTO) {
        return authorServiceImpl.authorInsert(authorDTO) +
                " number of rows inserted into the TABLE author.";
    }

    //TO-DO add UPDATE and DELETE functionalities
//
//    @PutMapping("updateAuthor/{id}")
//    public String updateAuthor(@RequestBody Author author, @PathVariable int id) {
//        return authorServiceImpl.updateAuthorRow(author, id) + " number of rows updated in TABLE author.";
//    }
//
//    @DeleteMapping("/book_management/deleteAuthor/{id}")
//    public String deleteAuthorById(@PathVariable int id) {
//        return authorServiceImpl.deleteAuthorRow(id) + " number of rows deleted from TABLE author.";
//    }
}