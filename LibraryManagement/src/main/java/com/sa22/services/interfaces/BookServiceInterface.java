package com.sa22.services.interfaces;

import com.sa22.entities.Book;
import java.util.List;

public interface BookServiceInterface {

    List<Book> mapBook(String filePath);
}
