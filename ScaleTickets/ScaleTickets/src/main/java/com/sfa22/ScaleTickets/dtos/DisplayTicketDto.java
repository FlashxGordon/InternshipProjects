package com.sfa22.ScaleTickets.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DisplayTicketDto {
    private int ticketId;
    private String clientName;

    private int seatNumber;
    private double ticketPrice;
    private String busPlate;
    private String departureCity;
    private String arrivalCity;
    private LocalDateTime departureDate;


    public DisplayTicketDto() {
    }

    public DisplayTicketDto(int ticketId, String clientName,
                            double ticketPrice, String busPlate,
                            String departureCity, String arrivalCity,
                            LocalDateTime departureDate,
                            int seatNumber) {
        this.ticketId = ticketId;
        this.clientName = clientName;
        this.ticketPrice = ticketPrice;
        this.busPlate = busPlate;
        this.departureCity = departureCity;
        this.arrivalCity = arrivalCity;
        this.departureDate = departureDate;
        this.seatNumber = seatNumber;
    }

    public DisplayTicketDto(String clientName, double ticketPrice,
                            String busPlate, String departureCity,
                            String arrivalCity, LocalDateTime departureDate,
                            int seatNumber) {
        this.clientName = clientName;
        this.ticketPrice = ticketPrice;
        this.busPlate = busPlate;
        this.departureCity = departureCity;
        this.arrivalCity = arrivalCity;
        this.departureDate = departureDate;
        this.seatNumber = seatNumber;
    }


}



