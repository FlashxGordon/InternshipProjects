package com.sfa22.ScaleTickets.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
public class TripDto {
    private int tripId;
    private LocalDateTime departureDate;
    private LocalDateTime arrivalDate;
    private int remainingSeats;
    private RouteDto route;
    private BusDto bus;

    public TripDto() {
    }

    public TripDto(int tripId, LocalDateTime departureDate, LocalDateTime arrivalDate, int remainingSeats, RouteDto route, BusDto bus) {
        this.tripId = tripId;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.remainingSeats = remainingSeats;
        this.route = route;
        this.bus = bus;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RouteDto {
        private int routeId;
        private String departureCity;
        private String arrivalCity;
        private double tripPrice;
        private Duration estimateTimeArrival;
        private double distance;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BusDto {
        private int busID;
        private String busPlate;
    }
}
