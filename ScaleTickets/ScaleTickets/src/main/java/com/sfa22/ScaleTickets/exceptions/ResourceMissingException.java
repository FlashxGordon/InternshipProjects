package com.sfa22.ScaleTickets.exceptions;

public class ResourceMissingException extends RuntimeException {
  private final String missingResourceName;
  public ResourceMissingException(String errorMessage, String missingResourceName) {
    super(errorMessage);
    this.missingResourceName = missingResourceName;
  }

  public String getMissingResourceName() {
    return missingResourceName;
  }
}
