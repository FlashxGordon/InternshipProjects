package com.sa22.trippy.user_management;

import com.sa22.trippy.config.DataSource;
import com.sa22.trippy.config.LoggerConfig;
import com.sa22.trippy.user_management.interfaces.UserRepository;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private static final String SQL_SELECT_ALL = "SELECT * FROM user_table;";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM user_table WHERE user_id=?;";
    private static final String SQL_SELECT_BY_EMAIL = "SELECT * FROM user_table WHERE user_email=?;";
    private static final String SQL_SELECT_BY_USER_NAME = "SELECT * FROM user_table WHERE user_name=?;";
    private static final String SQL_USER_INSERT = "INSERT INTO user_table (user_name, user_email, user_city, date_joined) VALUES (?, ?, ?, ?);";


    public List<User> findAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Connection connection = DataSource.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL);
            while (resultSet.next()) {
                userList.add(mapUser(resultSet));
            }
            resultSet.close();
        } catch (SQLException sqlException) {
            LoggerConfig.logSqlException(sqlException);
        }
        return userList;
    }

    public User findUserByEmail(String userEmail) {
        return findUserByParam(userEmail, SQL_SELECT_BY_EMAIL);
    }

    public User findUserByUserName(String userName) {
        return findUserByParam(userName, SQL_SELECT_BY_USER_NAME);
    }

    public User findUserById(int userId) {

        User user = null;
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_ID)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = mapUser(resultSet);
            }
            resultSet.close();
        } catch (SQLException sqlException) {
            LoggerConfig.logSqlException(sqlException);
        }
        return user;
    }


    public User userInsert(User user) {

        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_USER_INSERT)) {
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getUserEmail());
            preparedStatement.setString(3, user.getUserCity());
            preparedStatement.setDate(4, user.getDateJoined());

            int result = preparedStatement.executeUpdate();
            System.out.println("Number of user created: " + result);
        } catch (SQLException sqlException) {
            LoggerConfig.logSqlException(sqlException);
        }
        return user;
    }

    private User mapUser(ResultSet resultSet) throws SQLException {
        return new User(resultSet.getInt("user_id"), resultSet.getString("user_name"),
                resultSet.getString("user_email"),
                resultSet.getString("user_city"), resultSet.getDate("date_joined"));
    }

    private User findUserByParam(String userInput, String sql) {
        User user = null;
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, userInput);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = mapUser(resultSet);
            }
            resultSet.close();
        } catch (SQLException sqlException) {
            LoggerConfig.logSqlException(sqlException);
        }
        return user;
    }
}


