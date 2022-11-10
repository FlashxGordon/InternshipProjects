package com.sa22.LMA.library_management.book_management.author;

import com.sa22.LMA.library_management.book_management.repositories.author_repository.AuthorInserter;
import com.sa22.LMA.library_management.book_management.repositories.author_repository.AuthorReader;
import com.sa22.LMA.library_management.book_management.author.author_services.GetAllAuthorsService;
import com.sa22.LMA.library_management.book_management.author.author_services.GetAuthorByIdService;
import com.sa22.LMA.library_management.book_management.author.author_services.GetLastAuthor;
import com.sa22.LMA.library_management.book_management.author.author_services.InsertAuthorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImpl implements GetAllAuthorsService, GetAuthorByIdService,
        GetLastAuthor, InsertAuthorService {

    private final AuthorReader authorReader = new AuthorReader();
    private final AuthorInserter authorInserter = new AuthorInserter();

    @Override
    public List<Author> findAllAuthors() {
        return authorReader.findAllAuthors();
    }

    @Override
    public Author findAuthorById(int id) {
        return authorReader.findAuthorById(id);
    }

    @Override
    public Author findLastAuthor() {
        return authorReader.findLastAuthor();
    }

    @Override
    public Author authorInsert(AuthorDTO authorDTO) {
        return authorInserter.insertAuthor(authorDTO);
    }
}

