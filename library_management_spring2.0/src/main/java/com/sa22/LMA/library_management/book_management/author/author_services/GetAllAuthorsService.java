package com.sa22.LMA.library_management.book_management.author.author_services;

import com.sa22.LMA.library_management.book_management.author.Author;

import java.util.List;

public interface GetAllAuthorsService {

    List<Author> findAllAuthors();
}
