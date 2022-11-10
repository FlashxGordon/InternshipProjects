package com.sfa22.ScaleTickets.exceptions;

public class InvalidUserInputException extends RuntimeException {
  public InvalidUserInputException(String errorMessage) {
    super(errorMessage);
  }
}
