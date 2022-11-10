package com.sa22.LMA.library_management.book_management.repositories.book_repository;

import com.sa22.LMA.library_management.book_management.book.Book;
import com.sa22.LMA.library_management.book_management.book.BookDTO;
import com.sa22.LMA.library_management.config.DataSource;
import com.sa22.LMA.library_management.config.LoggerConfig;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class BookCreator {

    private final BookReader bookReader;

    public BookCreator() {
        this.bookReader = new BookReader();
    }

    public boolean createBook(BookDTO bookDTO){
        final String sqlBookInsert = "INSERT INTO book_author (book_name, author_name, issue_date) VALUES (?, ?, ?);";
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlBookInsert)){
            preparedStatement.setString(1,bookDTO.getBookName());
            preparedStatement.setString(2,bookDTO.getAuthorName());
            preparedStatement.setDate(3,bookDTO.getIssueDate());

            int result = preparedStatement.executeUpdate();
            System.out.println("Creation result: " + result);
        } catch (SQLException sqlException){
            System.out.println("Book creation error.");
            sqlException.printStackTrace();
            return false;
        }
        return true;
    }
}
