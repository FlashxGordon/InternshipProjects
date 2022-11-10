package com.sa22.LMA.library_management.book_management.repositories.author_repository;

import com.sa22.LMA.library_management.book_management.author.Author;
import com.sa22.LMA.library_management.config.DataSource;
import com.sa22.LMA.library_management.config.LoggerConfig;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AuthorReader {

    public final String sqlSelectAll = "SELECT * FROM author;";

    public List<Author> findAllAuthors() {
        List<Author> authorList = new ArrayList<>();
        try (Connection connection = DataSource.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlSelectAll);
            while (resultSet.next()) {
                authorList.add(getAuthor(resultSet));
            }
            resultSet.close();
        } catch (SQLException sqlException) {
            LoggerConfig.logSqlException(sqlException);
        }
        return authorList;
    }

    public Author findAuthorById(int authorId) {
        String sqlSelectById = "SELECT * FROM author WHERE id=?;";
        Author author = null;
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlSelectById)) {
             preparedStatement.setInt(1, authorId);
             ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                author = getAuthor(resultSet);
            }
            resultSet.close();
        } catch (SQLException sqlException) {
            LoggerConfig.logSqlException(sqlException);
        }
        return author;
    }

    public Author findLastAuthor() {
        Author author = null;
        try (Connection connection = DataSource.getConnection();
             Statement statement = connection.createStatement(
                     ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            ResultSet resultSet = statement.executeQuery(sqlSelectAll);
            if (resultSet.last()) {
                author = getAuthor(resultSet);
            }
            resultSet.close();
        } catch (SQLException sqlException) {
            System.out.println("SQL Exception - findLastAuthor");
            sqlException.printStackTrace();
        }
        return author;
    }

    private Author getAuthor(ResultSet resultSet) throws SQLException {
        return new Author(resultSet.getInt("id"), resultSet.getString("name"));
    }
}
