package com.sa22.LMA.library_management.book_management.repositories.author_repository;

import com.sa22.LMA.library_management.book_management.author.Author;
import com.sa22.LMA.library_management.book_management.author.AuthorDTO;
import com.sa22.LMA.library_management.config.DataSource;
import com.sa22.LMA.library_management.config.LoggerConfig;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Repository
public class AuthorInserter {

    private final AuthorReader authorReader;
    public AuthorInserter(){
        this.authorReader = new AuthorReader();
    }

    public Author insertAuthor(AuthorDTO authorDTO){
        Author author = null;
        String sqlSelectAll = "SELECT * FROM author;";
        try(Connection connection = DataSource.getConnection();
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE)){
            processResultSet(authorDTO.getName(),sqlSelectAll,statement);
            author = authorReader.findLastAuthor();
        } catch (SQLException sqlException){
            LoggerConfig.logSqlException(sqlException);
        }
        return author;
    }

    private void processResultSet(String authorName, String sqlSelectAll, Statement statement) throws SQLException {
        ResultSet resultSet = statement.executeQuery(sqlSelectAll);
        resultSet.moveToInsertRow();
        resultSet.updateString("name", authorName);
        resultSet.insertRow();
        resultSet.beforeFirst();
        resultSet.close();
    }
}
