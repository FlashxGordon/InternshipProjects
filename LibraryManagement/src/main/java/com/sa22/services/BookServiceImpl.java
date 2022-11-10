package com.sa22.services;

import com.sa22.data.DataReader;
import com.sa22.entities.Book;
import com.sa22.services.interfaces.BookServiceInterface;
import com.sa22.services.interfaces.CommonPaths;

import java.util.ArrayList;
import java.util.List;

public class BookServiceImpl implements BookServiceInterface, CommonPaths {

    /**
     * Takes in filePath and uses the DateReader class to
     * map all books
     * @param filePath
     * @return
     */

    @Override
    public List<Book> mapBook(String filePath) {

        List<Book> allBookLines = new ArrayList<>();
        List<String> mapBook = DataReader.readAllData(bookPath);
        for (String s : mapBook) {
            String[] splitElementArr = s
                    .replace("[", "")
                    .replace("]", "")
                    .split("_");
            Book book = new Book(splitElementArr[0], splitElementArr[1], splitElementArr[2], splitElementArr[3]);
            allBookLines.add(book);
        }

        return allBookLines;
    }

    public static void displayAllBooks(){
        List<String> listOfBooks = DataReader.readAllData(bookPath);
        for (String listOfBook : listOfBooks) {
            System.out.println(listOfBook);
        }
    }

}
