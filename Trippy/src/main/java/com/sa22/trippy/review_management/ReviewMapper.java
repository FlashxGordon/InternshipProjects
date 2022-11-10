package com.sa22.trippy.review_management;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReviewMapper {

    public Review mapReviewDtoToReview(ReviewDto reviewDto) {

        return new Review(reviewDto.getReviewId(), reviewDto.getUserId(), reviewDto.getUserName(),
                reviewDto.getVenueId(), reviewDto.getVenueName(), reviewDto.getReviewText(), reviewDto.getRating(),
                reviewDto.getDateCreated());
    }


    public ReviewDto mapReviewToReviewDto(Review review) {

        return new ReviewDto(review.getReviewId(),review.getUserId(), review.getUserName(), review.getVenueId(),
                review.getVenueName(), review.getReviewText(), review.getRating(), review.getDateCreated());

    }

    public List<ReviewDto> getAllReviewDto(List<Review> allReviews) {

        List<ReviewDto> allReviewsDto = new ArrayList<>();

        for (Review review : allReviews) {

            allReviewsDto.add(mapReviewToReviewDto(review));
        }
        return allReviewsDto;
    }

}
