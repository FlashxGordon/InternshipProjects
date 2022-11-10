package com.sfa22.ScaleTickets.dtos;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

@Data
public class TripRequestDto {
    @NotNull
    private LocalDateTime departureDate;
    @NotNull
    @PositiveOrZero(message = "Route id must be positive or zero")
    private Integer routeID;
    @NotNull
    @PositiveOrZero(message = "Bus id must be positive or zero")
    private Integer busID;
    @NotNull
    @PositiveOrZero(message = "Driver id must be positive or zero")
    private Integer driverID;

    public TripRequestDto() {
    }

    public TripRequestDto(LocalDateTime departureDate, int routeID, int busID, int driverID) {
        this.departureDate = departureDate;
        this.routeID = routeID;
        this.busID = busID;
        this.driverID = driverID;
    }

}
