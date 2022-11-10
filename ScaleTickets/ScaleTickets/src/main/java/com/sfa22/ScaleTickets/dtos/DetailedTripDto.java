package com.sfa22.ScaleTickets.dtos;

import com.sfa22.ScaleTickets.entities.Bus;
import com.sfa22.ScaleTickets.entities.Driver;
import com.sfa22.ScaleTickets.entities.Route;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DetailedTripDto {
    private int tripId;
    private LocalDateTime departureDate;
    private LocalDateTime arrivalDate;
    private int remainingSeats;
    private Route route;
    private Bus bus;
    private Driver driver;

    public DetailedTripDto() {
    }

    public DetailedTripDto(int tripId, LocalDateTime departureDate, LocalDateTime arrivalDate, int remainingSeats, Route route, Bus bus, Driver driver) {
        this.tripId = tripId;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.remainingSeats = remainingSeats;
        this.route = route;
        this.bus = bus;
        this.driver = driver;
    }
}
