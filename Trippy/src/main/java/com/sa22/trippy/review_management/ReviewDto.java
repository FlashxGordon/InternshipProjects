package com.sa22.trippy.review_management;

import java.sql.Date;
import java.time.LocalDate;

public class ReviewDto {

    private int reviewId;
    private int userId;
    private String userName;
    private int venueId;
    private String venueName;
    private String reviewText;
    private int rating;
    private Date dateCreated;


    public ReviewDto() {
    }


    public ReviewDto(int reviewId, int userId, String userName, int venueId,
                     String venueName, String reviewText, int rating, Date dateCreated) {
        this.reviewId = reviewId;
        this.userId = userId;
        this.userName = userName;
        this.venueId = venueId;
        this.venueName = venueName;
        this.reviewText = reviewText;
        this.rating = rating;
        this.dateCreated = dateCreated;
    }


    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getVenueId() {
        return venueId;
    }

    public void setVenueId(int venueId) {
        this.venueId = venueId;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Date getDateCreated() {
        return Date.valueOf(LocalDate.now());
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public String toString() {
        return "ReviewDto{" +
                "reviewId=" + reviewId +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", venueId=" + venueId +
                ", venueName='" + venueName + '\'' +
                ", reviewText='" + reviewText + '\'' +
                ", rating=" + rating +
                ", dateCreated=" + dateCreated +
                '}';
    }
}
