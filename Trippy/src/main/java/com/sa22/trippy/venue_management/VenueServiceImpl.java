package com.sa22.trippy.venue_management;

import com.sa22.trippy.exceptions.EmptyInputException;
import com.sa22.trippy.venue_management.interfaces.VenueService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class VenueServiceImpl implements VenueService {
    private final VenueRepositoryImpl venueRepositoryImpl;

    public VenueServiceImpl(VenueRepositoryImpl venueRepositoryImpl) {
        this.venueRepositoryImpl = venueRepositoryImpl;
    }


    @Override
    public List<Venue> getAllVenues() {
        if (venueRepositoryImpl.getAllVenues() == null ||
                venueRepositoryImpl.getAllVenues().isEmpty()) {
            throw new NoSuchElementException();
        }
        return venueRepositoryImpl.getAllVenues();
    }

    @Override
    public double getAverageRating(int venueId) {
        if (venueId < 1) {
            throw new NoSuchElementException();
        }
        List<Double> averageList = venueRepositoryImpl.getAverageRating(venueId);
        double avg = 0.0;
        for (Double aDouble : averageList) {
            avg += aDouble;
        }
        avg = avg / averageList.size();
        return avg;
    }

    @Override
    public List<Venue> getVenueByCity(String venueCity) {
        if (venueRepositoryImpl.findVenueByCity(venueCity) == null) {
            throw new NoSuchElementException();
        }
        return venueRepositoryImpl.findVenueByCity(venueCity);
    }


    @Override
    public List<Venue> getVenueByType(String venueType) {
        if (venueRepositoryImpl.findVenueByVenueType(venueType) == null) {
            throw new NoSuchElementException();
        }
        return venueRepositoryImpl.findVenueByVenueType(venueType);
    }

    @Override
    public Venue insertVenue(Venue venue) {
        if (venue.getVenueName().isEmpty() || (venue.getVenueName().length() == 0)
                || venue.getVenueEmail().isEmpty() || venue.getVenueCity().isEmpty()
                || venue.getVenuePhone().isEmpty()) {
            throw new EmptyInputException();
        }
        return venueRepositoryImpl.insertVenue(venue);
    }

    @Override
    public int updateVenue(Venue venue, int id) {
        return 0;
    }


}
