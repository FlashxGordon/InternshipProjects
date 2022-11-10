package com.sa22.trippy.review_management.interfaces;

import com.sa22.trippy.review_management.Review;

import java.util.List;

public interface ReviewRepository {

     boolean insertReview(Review review);

    List<Review> getAllReviews();

    Review getReviewById(int reviewId);

//    public List<Review> getLastReview();
//
}
