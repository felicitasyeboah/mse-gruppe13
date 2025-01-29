package de.cityfeedback.shared.exception;

import de.cityfeedback.shared.dto.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import java.util.NoSuchElementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
  Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  // Fängt alle Exceptions ab, die nicht spezifisch behandelt wurden
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse> handleGeneralException(Exception ex) {
    String message = "Unerwarteter Fehler: " + ex.getMessage();
    logger.error(message, ex);
    // Fehlerantwort erstellen
    ApiResponse response = new ApiResponse(message, null);
    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<ApiResponse> handleRunException(RuntimeException ex) {
    String message = "Unerwarteter Fehler: " + ex.getMessage();
    logger.error(message, ex);
    // Fehlerantwort erstellen
    ApiResponse response = new ApiResponse(message, null);
    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  // Spezifische Ausnahmebehandlung für NoSuchElementException (z.B. Benutzer nicht gefunden)
  @ExceptionHandler(NoSuchElementException.class)
  public ResponseEntity<ApiResponse> handleNoSuchElementException(NoSuchElementException ex) {
    String message = ex.getMessage();
    logger.error(message, ex);
    ApiResponse response = new ApiResponse(message, null);
    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ApiResponse> handleInvalidArgumentException(IllegalArgumentException ex) {
    String message = ex.getMessage();
    logger.error(message, ex);
    ApiResponse response = new ApiResponse(message, null);
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(WrongUserInputException.class)
  public ResponseEntity<ApiResponse> handleWrongUserInputException(WrongUserInputException ex) {
    String message = ex.getMessage();
    logger.error(message, ex);
    ApiResponse response = new ApiResponse(message, null);
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ApiResponse> handleEntityNotFoundException(EntityNotFoundException ex) {
    String message = ex.getMessage();
    logger.error(message, ex);
    ApiResponse response = new ApiResponse(message, null);
    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
  }
}
