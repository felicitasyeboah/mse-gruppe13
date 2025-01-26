package de.cityfeedback.shared.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import de.cityfeedback.shared.dto.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class GlobalExceptionHandlerTest {

  private GlobalExceptionHandler globalExceptionHandler;

  @BeforeEach
  void setUp() {
    globalExceptionHandler = new GlobalExceptionHandler();
  }

  @Test
  void handleGeneralException_returnsInternalServerError() {
    Exception exception = new Exception("General error");

    ResponseEntity<ApiResponse> response = globalExceptionHandler.handleGeneralException(exception);

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertEquals("Unerwarteter Fehler: General error", response.getBody().getMessage());
    assertNull(response.getBody().getData());
  }

  @Test
  void handleNoSuchElementException_returnsNotFound() {
    NoSuchElementException exception = new NoSuchElementException("Element not found");

    ResponseEntity<ApiResponse> response =
        globalExceptionHandler.handleNoSuchElementException(exception);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Element not found", response.getBody().getMessage());
    assertNull(response.getBody().getData());
  }

  @Test
  void handleInvalidArgumentException_returnsBadRequest() {
    IllegalArgumentException exception = new IllegalArgumentException("Invalid argument");

    ResponseEntity<ApiResponse> response =
        globalExceptionHandler.handleInvalidArgumentException(exception);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals("Fehler: Invalid argument", response.getBody().getMessage());
    assertNull(response.getBody().getData());
  }

  @Test
  void handleWrongUserInputException_returnsBadRequest() {
    WrongUserInputException exception = new WrongUserInputException("Wrong user input");

    ResponseEntity<ApiResponse> response =
        globalExceptionHandler.handleWrongUserInputException(exception);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals("Wrong user input", response.getBody().getMessage());
    assertNull(response.getBody().getData());
  }

  @Test
  void handleEntityNotFoundException_returnsNotFound() {
    EntityNotFoundException exception = new EntityNotFoundException("Entity not found");

    ResponseEntity<ApiResponse> response =
        globalExceptionHandler.handleEntityNotFoundException(exception);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Fehler: Entity not found", response.getBody().getMessage());
    assertNull(response.getBody().getData());
  }
}
