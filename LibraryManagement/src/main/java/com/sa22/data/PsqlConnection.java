package com.sa22.data;

import java.sql.*;

public class PsqlConnection {

    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/library_management";
    private static final String USER_NAME = "postgres";
    private static final String PASSWORD = "password";

    public static void connectToDB(String sql, String columnLabel) {

        try (Connection connection =
                     DriverManager.getConnection(JDBC_URL, USER_NAME, PASSWORD)) {
            System.out.println("Connected to PSQL server!");

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String column1 = resultSet.getString(columnLabel);
                System.out.println(resultSet.getRow() + " " + column1);
            }

        } catch (SQLException sqlException) {
            System.out.println("Error in connecting to PSQL server.");
            System.out.println(sqlException);
        }
    }
}
