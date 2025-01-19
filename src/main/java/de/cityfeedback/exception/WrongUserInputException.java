package de.cityfeedback.exception;

public class WrongUserInputException extends RuntimeException {
  public WrongUserInputException(String message) {
    super(message);
  }
}
