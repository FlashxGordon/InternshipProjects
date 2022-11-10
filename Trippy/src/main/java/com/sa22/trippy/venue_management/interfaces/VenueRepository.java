package com.sa22.trippy.venue_management.interfaces;

import com.sa22.trippy.venue_management.Venue;

import java.util.List;

public interface VenueRepository {

    List<Double> getAverageRating(int venueId);

    Venue getReviewCount(int venueId);

    List<Venue> getAllVenues();


}
