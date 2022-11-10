package com.sfa22.ScaleTickets.dtos;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.Duration;

@Data
public class RouteDto {

    private int routeId;

    @NotBlank(message = "Departure city must not be blank")
    private String departureCity;

    @NotBlank(message = "Arrival city must not be blank")
    private String arrivalCity;

    @NotNull(message = "Trip price should not be null")
    @Positive(message = "Trip price must be a positive number")
    private double tripPrice;

    private Duration travelDuration;

    private double distance;


    public RouteDto() {
    }

    public RouteDto(int routeId, String departureCity,
                    String arrivalCity, double tripPrice,
                    Duration travelDuration, double distance) {
        this.routeId = routeId;
        this.departureCity = departureCity;
        this.arrivalCity = arrivalCity;
        this.tripPrice = tripPrice;
        this.travelDuration = travelDuration;
        this.distance = distance;
    }

    public RouteDto(String departureCity, String arrivalCity,
                    double tripPrice, Duration travelDuration,
                    double distance) {
        this.departureCity = departureCity;
        this.arrivalCity = arrivalCity;
        this.tripPrice = tripPrice;
        this.travelDuration = travelDuration;
        this.distance = distance;
    }
}
