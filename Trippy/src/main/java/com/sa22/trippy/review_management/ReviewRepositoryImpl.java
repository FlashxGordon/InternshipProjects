package com.sa22.trippy.review_management;

import com.sa22.trippy.config.DataSource;
import com.sa22.trippy.config.LoggerConfig;
import com.sa22.trippy.review_management.interfaces.ReviewRepository;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReviewRepositoryImpl implements ReviewRepository {

    private static final String SQL_SELECT_ALL = "SELECT * FROM review_table;";

    public boolean insertReview(Review review) {

        final String sqlInsert = "INSERT INTO review_table (user_id, user_name, venue_id, " +
                "venue_name, review_text, rating, date_created) VALUES (?, ?, ?, ?, ?, ?, ?);";
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlInsert)) {

            preparedStatement.setInt(1, review.getUserId());
            preparedStatement.setString(2, review.getUserName());
            preparedStatement.setInt(3, review.getVenueId());
            preparedStatement.setString(4, review.getVenueName());
            preparedStatement.setString(5, review.getReviewText());
            preparedStatement.setInt(6, review.getRating());
            preparedStatement.setDate(7, review.getDateCreated());

            int executedUpdate = preparedStatement.executeUpdate();

            System.out.println("Number of executed updates: " + executedUpdate);

        } catch (SQLException sqlException) {

            LoggerConfig.logSqlException(sqlException);
            return false;
        }
        return true;
    }

    public List<Review> getAllReviews() {

        List<Review> reviewList = new ArrayList<>();

        try (Connection connection = DataSource.getConnection()) {
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL);

            mapAllReviews(resultSet, reviewList);

            resultSet.close();

        } catch (SQLException sqlException) {

            LoggerConfig.logSqlException(sqlException);
        }
        return reviewList;
    }

    public Review getReviewById(int reviewId) {

        String sqlSelectById = "SELECT * FROM review_table WHERE review_id=?;";

        Review findReview = null;

        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlSelectById)) {
            preparedStatement.setInt(1, reviewId);

            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();

            findReview = mapReview(resultSet);

            resultSet.close();

        } catch (SQLException sqlException) {
            LoggerConfig.logSqlException(sqlException);
        }
        return findReview;
    }


    private Review mapReview(ResultSet resultSet) throws SQLException {

        int reviewId = resultSet.getInt("review_id");
        int userId = resultSet.getInt("user_id");
        String userName = resultSet.getString("user_name");
        int venueId = resultSet.getInt("venue_id");
        String venueName = resultSet.getString("venue_name");
        String reviewText = resultSet.getString("review_text");
        int rating = resultSet.getInt("rating");
        Date dateCreated = resultSet.getDate("date_created");

        return new Review(reviewId, userId, userName,
                venueId, venueName, reviewText,
                rating, dateCreated);
    }


    private void mapAllReviews(ResultSet resultSet, List<Review> reviewList) throws SQLException {
        while (resultSet.next()) {
            Review reviewFromDb = mapReview(resultSet);
            reviewList.add(reviewFromDb);
        }
    }

}
