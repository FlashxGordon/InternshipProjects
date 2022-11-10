package com.sfa22.ScaleTickets.controllers;

import com.sfa22.ScaleTickets.dtos.DetailedTripDto;
import com.sfa22.ScaleTickets.dtos.TripDto;
import com.sfa22.ScaleTickets.dtos.TripRequestDto;
import com.sfa22.ScaleTickets.services.interfaces.TripService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class TripController {
    private final TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }


    @GetMapping("/trips/{id}")
    public ResponseEntity<TripDto> getTripById(@PathVariable int id) {

        return ResponseEntity.ok(tripService.getTrip(id));
    }


    @GetMapping(value = "/trips")
    public ResponseEntity<List<TripDto>> getTrips(
            @RequestParam(name = "departureDate", required = false) LocalDate departureDate,
            @RequestParam(name = "departureCity", required = false) String departureCity,
            @RequestParam(name = "arrivalCity", required = false) String arrivalCity,
            @RequestParam(name = "minimumRemainingSeats", required = false) Integer minimumRemainingSeats) {

        return ResponseEntity.ok(tripService.getByDepartureDateAndFilters(departureDate, departureCity, arrivalCity,
                minimumRemainingSeats));
    }


    @GetMapping(value = "/trips/afterDate")
    public ResponseEntity<List<TripDto>> getTripsByDepartureDateGreaterThanEqualsAndFilters(
            @RequestParam(name = "minimumDepartureDate") LocalDate minimumDepartureDate,
            @RequestParam(name = "departureCity", required = false) String departureCity,
            @RequestParam(name = "arrivalCity", required = false) String arrivalCity,
            @RequestParam(name = "minimumRemainingSeats", required = false) Integer remainingSeats) {

        return ResponseEntity.ok(tripService.getByDepartureDateGreaterThanEqualAndFilters(minimumDepartureDate,
                departureCity, arrivalCity, remainingSeats));
    }


    @GetMapping("/detailedTrips/{id}")
    public ResponseEntity<DetailedTripDto> getDetailedTripById(@PathVariable int id) {

        return ResponseEntity.ok(tripService.getDetailedTrip(id));
    }


    @GetMapping(value = "/detailedTrips")
    public ResponseEntity<List<DetailedTripDto>> getDetailedTrips(
            @RequestParam(name = "departureDate", required = false) LocalDate departureDate,
            @RequestParam(name = "departureCity", required = false) String departureCity,
            @RequestParam(name = "arrivalCity", required = false) String arrivalCity,
            @RequestParam(name = "minimumRemainingSeats", required = false) Integer minimumRemainingSeats) {

        return ResponseEntity.ok(tripService
                .getDetailedTripsByDepartureDateAndFilters(departureDate, departureCity, arrivalCity,
                        minimumRemainingSeats));
    }


    @GetMapping(value = "/detailedTrips/afterDate")
    public ResponseEntity<List<DetailedTripDto>> getDetailedTripsByDepartureDateGreaterThanEqualsAndFilters(
            @RequestParam(name = "minimumDepartureDate") LocalDate minimumDepartureDate,
            @RequestParam(name = "departureCity", required = false) String departureCity,
            @RequestParam(name = "arrivalCity", required = false) String arrivalCity,
            @RequestParam(name = "minimumRemainingSeats", required = false) Integer remainingSeats) {

        return ResponseEntity.ok(tripService.getDetailedTripsByDepartureDateGreaterThanEqualAndFilters(
                minimumDepartureDate, departureCity, arrivalCity, remainingSeats));
    }


    @GetMapping("routes/{routeId}/detailedTrips")
    public ResponseEntity<List<DetailedTripDto>> getDetailedTripByRouteId(@PathVariable int routeId) {

        return ResponseEntity.ok(tripService.getDetailedTripsByRoute(routeId));
    }


    @GetMapping("buses/{busId}/detailedTrips")
    public ResponseEntity<List<DetailedTripDto>> getDetailedTripByBusId(@PathVariable int busId) {

        return ResponseEntity.ok(tripService.getDetailedTripsByBus(busId));
    }


    @GetMapping("drivers/{driverId}/detailedTrips")
    public ResponseEntity<List<DetailedTripDto>> getDetailedTripByDriverId(@PathVariable int driverId) {

        return ResponseEntity.ok(tripService.getDetailedTripsByDriver(driverId));
    }


    @PostMapping(value = "/detailedTrips")
    public ResponseEntity<Void> addTrip(@Valid @RequestBody TripRequestDto tripDto) {

        int newTripId = tripService.addTrip(tripDto);

        return ResponseEntity.created(URI.create("/api/v1/detailedTrips/" + newTripId)).build();
    }


    @PutMapping(value = "/detailedTrips/{id}")
    public ResponseEntity<Void> updateTrip(@PathVariable int id, @Valid @RequestBody TripRequestDto tripDto) {

        tripService.updateTrip(id, tripDto);

        return ResponseEntity.ok().build();
    }


    @DeleteMapping(value = "/detailedTrips/{id}")
    public ResponseEntity<Void> deleteTrip(@PathVariable int id) {

        tripService.deleteTrip(id);

        return ResponseEntity.noContent().build();
    }
}
