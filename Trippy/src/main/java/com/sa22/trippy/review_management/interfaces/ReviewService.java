package com.sa22.trippy.review_management.interfaces;

import com.sa22.trippy.review_management.Review;
import com.sa22.trippy.review_management.ReviewDto;

import java.util.List;

public interface ReviewService {

    Review getReviewById(int id);

    List<ReviewDto> getAllReviews();

    boolean insertReview(Review review);

    int updateReview(ReviewDto reviewDto, int id);

    int deleteReview(int id);


}
