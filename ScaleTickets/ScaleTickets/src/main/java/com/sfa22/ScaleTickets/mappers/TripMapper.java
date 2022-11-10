package com.sfa22.ScaleTickets.mappers;

import com.sfa22.ScaleTickets.dtos.DetailedTripDto;
import com.sfa22.ScaleTickets.dtos.TripDto;
import com.sfa22.ScaleTickets.dtos.TripRequestDto;
import com.sfa22.ScaleTickets.entities.Bus;
import com.sfa22.ScaleTickets.entities.Driver;
import com.sfa22.ScaleTickets.entities.Route;
import com.sfa22.ScaleTickets.entities.Trip;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TripMapper {
    public TripDto mapTripToTripDto(Trip trip) {
        Route route = trip.getRoute();
        return new TripDto(trip.getTripId(), trip.getDepartureDate(), trip.getArrivalDate(), trip.getRemainingSeats(),
                new TripDto.RouteDto(route.getRouteId(), route.getDepartureCity(),
                        route.getArrivalCity(), route.getTripPrice(), route.getTravelDuration(), route.getDistance()),
                new TripDto.BusDto(trip.getBus().getBusID(), trip.getBus().getBusPlate()));
    }

    public DetailedTripDto mapTripToDetailedTripDto(Trip trip) {
        return new DetailedTripDto(trip.getTripId(), trip.getDepartureDate(), trip.getArrivalDate(),
                trip.getRemainingSeats(), trip.getRoute(), trip.getBus(), trip.getDriver());
    }

    public Trip mapTripRequestDtoToTrip(TripRequestDto tripRequestDto, Route route, Bus bus, Driver driver) {
        return new Trip(tripRequestDto.getDepartureDate(), route, bus, driver);
    }

    public Trip mapTripRequestDtoToTrip(int tripId, TripRequestDto tripRequestDto, Route route, Bus bus, Driver driver) {
        return new Trip(tripId, tripRequestDto.getDepartureDate(), route, bus, driver);
    }

    public List<TripDto> mapListOfTripToTripDto(List<Trip> trips) {
        return trips.stream().map(this::mapTripToTripDto).collect(Collectors.toList());
    }

    public List<DetailedTripDto> mapListOfTripToDetailedTripDto(List<Trip> trips) {
        return trips.stream().map(this::mapTripToDetailedTripDto)
                .collect(Collectors.toList());
    }
}
