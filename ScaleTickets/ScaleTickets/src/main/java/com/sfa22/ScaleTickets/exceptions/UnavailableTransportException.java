package com.sfa22.ScaleTickets.exceptions;

public class UnavailableTransportException extends RuntimeException {
  public UnavailableTransportException(String errorMessage) {
    super(errorMessage);
  }
}
