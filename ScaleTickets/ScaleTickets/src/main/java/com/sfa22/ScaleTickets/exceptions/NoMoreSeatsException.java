package com.sfa22.ScaleTickets.exceptions;

public class NoMoreSeatsException extends RuntimeException{

    public NoMoreSeatsException(String message) {
        super(message);
    }
}
