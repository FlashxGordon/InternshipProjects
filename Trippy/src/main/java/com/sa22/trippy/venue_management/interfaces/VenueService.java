package com.sa22.trippy.venue_management.interfaces;

import com.sa22.trippy.venue_management.Venue;

import java.util.List;

public interface VenueService {

    List<Venue> getAllVenues();

    double getAverageRating(int venueId);

    List<Venue> getVenueByCity(String venueCity);

    List<Venue> getVenueByType(String venueType);

    Venue insertVenue(Venue venue);

    int updateVenue(Venue venue, int id);

}





