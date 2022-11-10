package com.sa22.LMA.library_management.book_management.repositories.book_repository;

import com.sa22.LMA.library_management.book_management.book.Book;
import com.sa22.LMA.library_management.config.DataSource;
import com.sa22.LMA.library_management.config.LoggerConfig;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BookReader {

    public final String sqlSelectAll = "SELECT * FROM book_author;";

    public List<Book> findAllBooks() {
        List<Book> bookList = new ArrayList<>();
        try (Connection connection = DataSource.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlSelectAll);
            while (resultSet.next()) {
                bookList.add(getBook(resultSet));
            }
            resultSet.close();
        } catch (SQLException sqlException) {
            LoggerConfig.logSqlException(sqlException);
        }
        return bookList;
    }

    public Book findBookById(int bookId) {
        String sqlSelectById = "SELECT * FROM book_author WHERE id=?;";
        Book book = null;
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlSelectById)) {
            preparedStatement.setInt(1, bookId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                book = getBook(resultSet);
            }
            resultSet.close();
        } catch (SQLException sqlException) {
            LoggerConfig.logSqlException(sqlException);
        }
        return book;
    }

    public Book findLastBook() {
        Book book = null;
        try (Connection connection = DataSource.getConnection();
             Statement statement = connection.createStatement(
                     ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            ResultSet resultSet = statement.executeQuery(sqlSelectAll);
            if (resultSet.last()) {
                book = getBook(resultSet);
            }
            resultSet.close();
        } catch (SQLException sqlException) {
            System.out.println("SQL Exception - findLastBook");
            sqlException.printStackTrace();
        }
        return book;
    }

    private Book getBook(ResultSet resultSet) throws SQLException {
        return new Book(resultSet.getInt("id"), resultSet.getString("book_name"),
                resultSet.getString("author_name"),
                resultSet.getDate("issue_date"));
    }
}