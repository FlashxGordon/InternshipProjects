package com.sfa22.ScaleTickets.dtos;

import lombok.Data;

@Data
public class InputTicketDto {

    private String clientName;
    private String email;
    private String phoneNumber;
    private int tripId;
    private String discountCode;

    public InputTicketDto() {
    }

    public InputTicketDto(String clientName, String email, String phoneNumber,
                          int tripId, String discountCode) {
        this.clientName = clientName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.tripId = tripId;
        this.discountCode = discountCode;
    }

    public InputTicketDto(String clientName, String email, String phoneNumber, int tripId) {
        this.clientName = clientName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.tripId = tripId;
    }
}
