package com.sa22.services.interfaces;

import com.sa22.entities.Author;
import java.util.List;

public interface AuthorServiceInterface {

    List<Author> mapAuthor(String filePath);

}