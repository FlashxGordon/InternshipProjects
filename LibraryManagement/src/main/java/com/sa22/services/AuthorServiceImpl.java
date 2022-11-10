package com.sa22.services;

import com.sa22.data.DataReader;
import com.sa22.entities.Author;
import com.sa22.entities.Book;
import com.sa22.services.interfaces.AuthorServiceInterface;
import com.sa22.services.interfaces.CommonPaths;

import java.util.ArrayList;
import java.util.List;

public class AuthorServiceImpl implements AuthorServiceInterface, CommonPaths {

    /**
     *Takes in predefined file path
     * maps all authors and their IDs
     * @param filePath
     * @return allAuthors (list of all authors)
     */

    @Override
    public List<Author> mapAuthor(String filePath) {

        List<Author> allAuthors = new ArrayList<>();
        List<String> mapAuthor = DataReader.readAllData(authorPath);
        for (String s : mapAuthor) {
            String[] splitElementArr = s
                    .replace("[", "")
                    .replace("]", "")
                    .split("_");
            Author author = new Author(Integer.parseInt(splitElementArr[0]), splitElementArr[1]);
            allAuthors.add(author);
        }

        return allAuthors;
    }

}

