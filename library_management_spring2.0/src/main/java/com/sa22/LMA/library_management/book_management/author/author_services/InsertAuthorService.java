package com.sa22.LMA.library_management.book_management.author.author_services;

import com.sa22.LMA.library_management.book_management.author.Author;
import com.sa22.LMA.library_management.book_management.author.AuthorDTO;

public interface InsertAuthorService {
    Author authorInsert(AuthorDTO authorDTO);
}
