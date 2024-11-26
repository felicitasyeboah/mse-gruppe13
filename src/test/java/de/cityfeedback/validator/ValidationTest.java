package de.cityfeedback.validator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import de.cityfeedback.exception.WrongUserInputException;
import org.junit.jupiter.api.Test;

public class ValidationTest {
  // Test für die Validierung des Benutzernamens
  @Test
  public void testValidateUsername_Valid() {
    // Gültige Benutzernamen
    assertDoesNotThrow(() -> Validation.validateUsername("max_mustermann"));
    assertDoesNotThrow(() -> Validation.validateUsername("user123"));
  }

  @Test
  public void testValidateUsername_Invalid() {
    // Ungültige Benutzernamen
    assertThrows(WrongUserInputException.class, () -> Validation.validateUsername("a")); // Zu kurz
    assertThrows(
        WrongUserInputException.class,
        () -> Validation.validateUsername("user@123")); // Ungültige Zeichen
    assertThrows(
        WrongUserInputException.class,
        () -> Validation.validateUsername("EinsehrsehrsehsehrsehrlangerName")); // Zu lang
  }

  // Test für die Validierung der E-Mail-Adresse
  @Test
  public void testValidateEmail_Valid() {
    // Gültige E-Mail-Adressen
    assertDoesNotThrow(() -> Validation.validateEmail("example@example.com"));
    assertDoesNotThrow(() -> Validation.validateEmail("user.name+tag@domain.de"));
  }

  @Test
  public void testValidateEmail_Invalid() {
    // Ungültige E-Mail-Adressen
    assertThrows(WrongUserInputException.class, () -> Validation.validateEmail("invalid-email"));
    assertThrows(WrongUserInputException.class, () -> Validation.validateEmail("user@domain"));
    assertThrows(WrongUserInputException.class, () -> Validation.validateEmail("user@domain,com"));
  }

  // Test für die Validierung des Beschwerdetitels
  @Test
  public void testValidateComplaintTitle_Valid() {
    // Gültige Titel
    assertDoesNotThrow(() -> Validation.validateComplaintTitle("Verkehrsproblem"));
    assertDoesNotThrow(
        () -> Validation.validateComplaintTitle("Sehr langer Titel, aber immer noch gültig"));
  }

  @Test
  public void testValidateComplaintTitle_Invalid() {
    // Ungültige Titel
    assertThrows(
        WrongUserInputException.class, () -> Validation.validateComplaintTitle("")); // Zu kurz
    assertThrows(
        WrongUserInputException.class, () -> Validation.validateComplaintTitle("A")); // Zu kurz
    assertThrows(
        WrongUserInputException.class,
        () ->
            Validation.validateComplaintTitle(
                "Sehr sehr sehr sehr sehr sehr sehr sehr sehr sehr sehr sehr sehr langer Titel, und deshalb nicht mehr gültig")); // Zu lang
  }

  // Test für die Validierung der Beschreibungen
  @Test
  public void testValidateComplaintContent_Valid() {
    // Gültige Beschreibungen
    assertDoesNotThrow(
        () -> Validation.validateComplaintContent("Es gibt ein Problem auf der Straße"));
    assertDoesNotThrow(() -> Validation.validateComplaintContent("Kurze Beschreibung."));
  }

  @Test
  public void testValidateComplaintContent_Invalid() {
    // Ungültige Beschreibungen
    assertThrows(
        WrongUserInputException.class, () -> Validation.validateComplaintContent("")); // Zu kurz
    assertThrows(
        WrongUserInputException.class,
        () -> Validation.validateComplaintContent("kurz")); // Zu kurz
    assertThrows(
        WrongUserInputException.class,
        () -> Validation.validateComplaintContent("A".repeat(501))); // Zu lang
  }

  /**
   * // Test für die Validierung der Kategorien @Test void testValidateCategory_Valid() { // Gültige
   * Kategorien assertDoesNotThrow(() -> Validation.validateCategory("Verkehr"));
   * assertDoesNotThrow(() -> Validation.validateCategory("Umwelt")); assertDoesNotThrow(() ->
   * Validation.validateCategory("Sicherheit")); } @Test void testValidateCategory_Invalid() { //
   * Ungültige Kategorien assertThrows(WrongUserInputException.class, () ->
   * Validation.validateCategory("Bildung")); assertThrows(WrongUserInputException.class, () ->
   * Validation.validateCategory("Sport")); assertThrows(WrongUserInputException.class, () ->
   * Validation.validateCategory("")); // Leerer Wert } *
   */
}
