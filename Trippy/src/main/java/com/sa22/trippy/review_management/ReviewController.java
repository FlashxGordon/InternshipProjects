package com.sa22.trippy.review_management;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/review")
public class ReviewController {

    private final ReviewServiceImpl reviewServiceImpl;

    ReviewController(ReviewServiceImpl reviewServiceImpl) {
        this.reviewServiceImpl = reviewServiceImpl;
    }

    @GetMapping("/all_reviews")
    public List<ReviewDto> findAllReviews() {
        return reviewServiceImpl.getAllReviews();
    }

    @GetMapping("/{id}")
    public Review getReviewById(@PathVariable int id) {
        return reviewServiceImpl.getReviewById(id);
    }

    @PostMapping("/review_new")
    public boolean insertReview(@RequestBody Review review){

        int id = 0;

        return reviewServiceImpl.insertReview(review);
    }

    @PutMapping("/{id}")
    public String updateReview(@RequestBody ReviewDto reviewDto, @PathVariable int id) {
        return reviewServiceImpl.updateReview(reviewDto, id) + " number of rows updated in TABLE review_table.";
    }

    @DeleteMapping("/{id}")
    public String deleteReview(@PathVariable int id) {
        return reviewServiceImpl.deleteReview(id) + " number of rows deleted from TABLE review_table.";
    }

}
