package com.sfa22.ScaleTickets.exceptions;

public class InvalidCredentialsException extends RuntimeException {
  public InvalidCredentialsException(String errorMessage) {
    super(errorMessage);
  }
}
