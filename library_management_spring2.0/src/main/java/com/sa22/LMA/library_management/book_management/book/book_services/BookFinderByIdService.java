package com.sa22.LMA.library_management.book_management.book.book_services;

import com.sa22.LMA.library_management.book_management.book.Book;

public interface BookFinderByIdService {

    Book findBookById(int bookId);
}
