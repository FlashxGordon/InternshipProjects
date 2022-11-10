package com.sa22.trippy.venue_management;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/venue")
public class VenueController {


    private final VenueServiceImpl venueServiceImpl;

    public VenueController(VenueServiceImpl venueServiceImpl) {
        this.venueServiceImpl = venueServiceImpl;
    }

    @GetMapping("/all_venues")
    public @ResponseBody ResponseEntity<List<Venue>> getAllVenues() {
        return ResponseEntity.ok(venueServiceImpl.getAllVenues());
    }

    @GetMapping("/average_rating/{venueId}")
    public ResponseEntity<Double> getAverageRating(@PathVariable int venueId) {
        if (venueId < 1) {
            throw new NoSuchElementException();
        }
        Double ratingRetrieved = venueServiceImpl.getAverageRating(venueId);
        return new ResponseEntity<Double>(ratingRetrieved, HttpStatus.OK);
    }


    @GetMapping("/venue_type/{venueType}")
    public List<Venue> getVenueByType(@PathVariable String venueType) {
        return venueServiceImpl.getVenueByType(venueType);
    }


    @GetMapping("/venue_city/{venueCity}")
    public List<Venue> getVenueByCity(@PathVariable String venueCity) {
        return venueServiceImpl.getVenueByCity(venueCity);
    }

    @PostMapping("/new_venue")
    public ResponseEntity<?> insertVenue(@RequestBody Venue venue) {
        Venue venueInserted = venueServiceImpl.insertVenue(venue);
        return new ResponseEntity<Venue>(venueInserted, HttpStatus.CREATED);

    }

}