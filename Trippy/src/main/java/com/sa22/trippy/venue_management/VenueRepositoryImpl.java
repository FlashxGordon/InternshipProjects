package com.sa22.trippy.venue_management;

import com.sa22.trippy.config.DataSource;
import com.sa22.trippy.config.LoggerConfig;
import com.sa22.trippy.user_management.User;
import com.sa22.trippy.venue_management.interfaces.VenueRepository;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class VenueRepositoryImpl implements VenueRepository {

    private static final String SQL_SELECT_ALL = "SELECT * FROM venue_table;";
    private static final String SQL_SELECT_AVG = "SELECT rating FROM review_table WHERE venue_id =?;";
    private static final String SQL_SELECT_COUNT = "SELECT COUNT(rating) FROM review_table WHERE venue_id=?;";
    private static final String SQL_SELECT_BY_CITY = "SELECT * FROM venue_table WHERE venue_city=?;";
    private static final String SQL_SELECT_BY_VENUE_TYPE = "SELECT * FROM venue_table WHERE venue_type=?;";

    private static final String SQL_USER_INSERT = "INSERT INTO venue_table (venue_name, venue_type, " +
            "venue_city, venue_address, venue_email, venue_phone) VALUES (?, ?, ?, ?, ?, ?);";



    public Venue insertVenue(Venue venue) {

        try (Connection connection = DataSource.getConnection();

             PreparedStatement preparedStatement = connection.prepareStatement(SQL_USER_INSERT)) {

            preparedStatement.setString(1, venue.getVenueName());
            preparedStatement.setString(2, venue.getVenueType());
            preparedStatement.setString(3, venue.getVenueCity());
            preparedStatement.setString(4, venue.getVenueAddress());
            preparedStatement.setString(5, venue.getVenueEmail());
            preparedStatement.setString(6, venue.getVenuePhone());

            int result = preparedStatement.executeUpdate();

            System.out.println("Number of venues created: " + result);

        } catch (SQLException sqlException) {
            LoggerConfig.logSqlException(sqlException);
        }
        return venue;
    }

    public List<Venue> findVenueByCity(String venueCity) {
        return findVenueByParam(venueCity, SQL_SELECT_BY_CITY);
    }

    public List<Venue> findVenueByVenueType(String venueType) {
        return findVenueByParam(venueType, SQL_SELECT_BY_VENUE_TYPE);
    }


    public List<Double> getAverageRating(int venueId) {

        List<Double> venueRatingList = new ArrayList<>();
        try (Connection connection = DataSource.getConnection();

             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_AVG)) {

            preparedStatement.setInt(1, venueId);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                venueRatingList.add(getReviewRating(resultSet));
            }

            resultSet.close();
        } catch (SQLException sqlException) {
            LoggerConfig.logSqlException(sqlException);
        }
        return venueRatingList;
    }

    public Venue getReviewCount(int venueId) {

        Venue reviewCount = null;
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_COUNT)) {

            preparedStatement.setInt(1, venueId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                reviewCount = getReviewCount(resultSet);
            }
            resultSet.close();
        } catch (SQLException sqlException) {
            LoggerConfig.logSqlException(sqlException);
        }
        return reviewCount;
    }

    public List<Venue> getAllVenues() {

        List<Venue> venueList = new ArrayList<>();

        try (Connection connection = DataSource.getConnection()) {

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL);

            while (resultSet.next()) {
                venueList.add(mapVenue(resultSet));
            }
            resultSet.close();
        } catch (SQLException sqlException) {
            LoggerConfig.logSqlException(sqlException);
        }
        return venueList;
    }

    private double getReviewRating(ResultSet resultSet) throws SQLException {
        return (resultSet.getDouble("rating"));
    }

    private Venue getReviewCount(ResultSet resultSet) throws SQLException {
        return new Venue(resultSet.getInt("rating"));
    }

    private Venue mapVenue(ResultSet resultSet) throws SQLException {
        return new Venue(resultSet.getInt("venue_id"),
                resultSet.getString("venue_name"),
                resultSet.getString("venue_type"),
                resultSet.getString("venue_city"),
                resultSet.getString("venue_address"),
                resultSet.getString("venue_email"),
                resultSet.getString("venue_phone"));
    }

    private List<Venue> findVenueByParam(String userInput, String sql) {
        List<Venue> venueList = new ArrayList<>();
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, userInput);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                venueList.add(mapVenue(resultSet));
            }
            resultSet.close();
        } catch (SQLException sqlException) {

            LoggerConfig.logSqlException(sqlException);
        }
        return venueList;
    }
}

