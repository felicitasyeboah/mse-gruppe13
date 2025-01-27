package de.cityfeedback.shared.exception;

public class WrongUserInputException extends RuntimeException {
  public WrongUserInputException(String message) {
    super(message);
  }
}
