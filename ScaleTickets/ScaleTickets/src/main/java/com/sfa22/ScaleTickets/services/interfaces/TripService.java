package com.sfa22.ScaleTickets.services.interfaces;

import com.sfa22.ScaleTickets.dtos.DetailedTripDto;
import com.sfa22.ScaleTickets.dtos.TripDto;
import com.sfa22.ScaleTickets.dtos.TripRequestDto;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.util.List;

public interface TripService {
  TripDto getTrip(int id);

  List<TripDto> getByDepartureDateAndFilters(
          @Nullable LocalDate departureDate,
          @Nullable String departureCity,
          @Nullable String arrivalCity,
          @Nullable Integer remainingSeats);

  List<TripDto> getByDepartureDateGreaterThanEqualAndFilters(
          @Nullable LocalDate departureDate,
          @Nullable String departureCity,
          @Nullable String arrivalCity,
          @Nullable Integer remainingSeats);


  DetailedTripDto getDetailedTrip(int id);

  List<DetailedTripDto> getDetailedTripsByDepartureDateAndFilters(
          @Nullable LocalDate departureDate,
          @Nullable String departureCity,
          @Nullable String arrivalCity,
          @Nullable Integer remainingSeats);

  List<DetailedTripDto> getAllDetailedTrips();

  List<DetailedTripDto> getDetailedTripsByRoute(int routeId);

  List<DetailedTripDto> getDetailedTripsByBus(int busId);

  List<DetailedTripDto> getDetailedTripsByDriver(int driverId);

  List<DetailedTripDto> getDetailedTripsByDepartureDateGreaterThanEqualAndFilters(
          @Nullable LocalDate departureDate,
          @Nullable String departureCity,
          @Nullable String arrivalCity,
          @Nullable Integer remainingSeats);

  int addTrip(TripRequestDto tripRequestDto);

  int updateTrip(int id, TripRequestDto tripRequestDto);

  void deleteTrip(int id);
}
