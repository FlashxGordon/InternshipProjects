package com.sfa22.ScaleTickets.validations;

import com.sfa22.ScaleTickets.entities.Bus;
import com.sfa22.ScaleTickets.entities.Trip;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class TripValidation {
  public boolean isDepartureDateBeforeArrivalDate(LocalDateTime departureDate, LocalDateTime arrivalDate) {
    return departureDate.isBefore(arrivalDate);
  }
  public boolean isFreeForNewTrip(LocalDateTime departureDate, LocalDateTime arrivalDate, List<Trip> trips) {
    for (Trip trip : trips) {
      if (isPeriodOverlapping(departureDate, arrivalDate, trip.getDepartureDate(), trip.getArrivalDate())) {
        return false;
      }
    }
    return true;
  }

  private boolean isPeriodOverlapping(LocalDateTime start1, LocalDateTime end1, LocalDateTime start2,
          LocalDateTime end2) {
    return (start1.equals(end2) || start1.isBefore(end2)) && (start2.isBefore(end1) || start2.equals(end1));
  }

  public boolean isNewBusSeatCountEqualOrHigherThanSoldSeats(Trip oldTrip, Bus newBus) {
    return calculateSoldSeats(oldTrip) <= newBus.getBusCapacity();
  }

  private int calculateSoldSeats(Trip trip) {
    return trip.getBus().getBusCapacity() - trip.getRemainingSeats();
  }
}
