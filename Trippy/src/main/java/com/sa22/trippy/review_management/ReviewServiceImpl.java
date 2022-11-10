package com.sa22.trippy.review_management;

import com.sa22.trippy.review_management.interfaces.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepositoryImpl reviewRepositoryImpl;


    public ReviewServiceImpl(ReviewRepositoryImpl reviewRepositoryImpl) {
        this.reviewRepositoryImpl = reviewRepositoryImpl;

    }

    @Override
    public Review getReviewById(int id) {
        return reviewRepositoryImpl.getReviewById(id);


    }

    @Override
    public List<ReviewDto> getAllReviews() {
        List<Review> reviewList = reviewRepositoryImpl.getAllReviews();
      return reviewList.stream().
              map(this::fromEntityToDto).
              collect(Collectors.toList());
    }
    public ReviewDto fromEntityToDto(Review review) {
        return new ReviewDto(review.getReviewId(), review.getUserId(), review.getUserName(),
                review.getVenueId(), review.getVenueName(), review.getReviewText(),
                review.getRating(), review.getDateCreated());

    }
    @Override
    public boolean insertReview(Review review) {


        return reviewRepositoryImpl.insertReview(review);
    }

    @Override
    public int updateReview(ReviewDto reviewDto, int id) {
        return 0;
    }

    @Override
    public int deleteReview(int id) {
        return 0;
    }


}
