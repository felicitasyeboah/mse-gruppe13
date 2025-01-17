/*package de.cityfeedback.userverwaltung.ui.controller;

import de.cityfeedback.feedbackverwaltung.application.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Fängt alle Exceptions ab, die nicht spezifisch behandelt wurden
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleGeneralException(Exception ex) {
        // Fehlerantwort erstellen
        ApiResponse response = new ApiResponse("Unerwarteter Fehler: " + ex.getMessage(), null);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Spezifische Ausnahmebehandlung für NoSuchElementException (z.B. Benutzer nicht gefunden)
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ApiResponse> handleUserNotFoundException(NoSuchElementException ex) {
        ApiResponse response = new ApiResponse("Benutzer nicht gefunden: " + ex.getMessage(), null);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // Spezifische Ausnahmebehandlung für IllegalArgumentException (z.B. ungültige Eingabe)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse> handleInvalidArgumentException(IllegalArgumentException ex) {
        ApiResponse response = new ApiResponse("Ungültige Eingabe: " + ex.getMessage(), null);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}*/
