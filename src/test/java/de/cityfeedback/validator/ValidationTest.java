package de.cityfeedback.validator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;


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
        assertThrows(IllegalArgumentException.class, () -> Validation.validateUsername("a"));  // Zu kurz
        assertThrows(IllegalArgumentException.class, () -> Validation.validateUsername("user@123"));  // Ungültige Zeichen
        assertThrows(IllegalArgumentException.class, () -> Validation.validateUsername("EinsehrsehrsehsehrsehrlangerName"));  // Zu lang
    }

    // Test für die Validierung der E-Mail-Adresse
    @Test
    public void testValidateEmail_Valid() {
        // Gültige E-Mail-Adressen
        assertDoesNotThrow(() -> Validation.validateEmail("example@example.com"));
        assertDoesNotThrow(() -> Validation.validateEmail("user.name+tag@domain.de"));
    }

    @Test
    void testValidateEmail_Invalid() {
        // Ungültige E-Mail-Adressen
        assertThrows(IllegalArgumentException.class, () -> Validation.validateEmail("invalid-email"));
        assertThrows(IllegalArgumentException.class, () -> Validation.validateEmail("user@domain"));
        assertThrows(IllegalArgumentException.class, () -> Validation.validateEmail("user@domain,com"));
    }

    // Test für die Validierung des Beschwerdetitels
    @Test
    void testValidateComplaintTitle_Valid() {
        // Gültige Titel
        assertDoesNotThrow(() -> Validation.validateComplaintTitle("Verkehrsproblem"));
        assertDoesNotThrow(() -> Validation.validateComplaintTitle("Sehr langer Titel, aber immer noch gültig"));
    }

    @Test
    void testValidateComplaintTitle_Invalid() {
        // Ungültige Titel
        assertThrows(IllegalArgumentException.class, () -> Validation.validateComplaintTitle(""));  // Zu kurz
        assertThrows(IllegalArgumentException.class, () -> Validation.validateComplaintTitle("A"));  // Zu kurz
        assertThrows(IllegalArgumentException.class, () -> Validation.validateComplaintTitle("Sehr sehr sehr sehr sehr sehr sehr sehr sehr sehr sehr sehr sehr langer Titel, und deshalb nicht mehr gültig"));  // Zu lang
    }

    // Test für die Validierung der Beschreibungen
    @Test
    void testValidateComplaintDescription_Valid() {
        // Gültige Beschreibungen
        assertDoesNotThrow(() -> Validation.validateComplaintDescription("Es gibt ein Problem auf der Straße"));
        assertDoesNotThrow(() -> Validation.validateComplaintDescription("Kurze Beschreibung."));
    }

    @Test
    void testValidateComplaintDescription_Invalid() {
        // Ungültige Beschreibungen
        assertThrows(IllegalArgumentException.class, () -> Validation.validateComplaintDescription(""));  // Zu kurz
        assertThrows(IllegalArgumentException.class, () -> Validation.validateComplaintDescription("Kurz"));  // Zu kurz
        assertThrows(IllegalArgumentException.class, () -> Validation.validateComplaintDescription("A".repeat(501)));  // Zu lang
    }

    /**  // Test für die Validierung der Kategorien
     @Test void testValidateCategory_Valid() {
     // Gültige Kategorien
     assertDoesNotThrow(() -> Validation.validateCategory("Verkehr"));
     assertDoesNotThrow(() -> Validation.validateCategory("Umwelt"));
     assertDoesNotThrow(() -> Validation.validateCategory("Sicherheit"));
     }

     @Test void testValidateCategory_Invalid() {
     // Ungültige Kategorien
     assertThrows(IllegalArgumentException.class, () -> Validation.validateCategory("Bildung"));
     assertThrows(IllegalArgumentException.class, () -> Validation.validateCategory("Sport"));
     assertThrows(IllegalArgumentException.class, () -> Validation.validateCategory(""));  // Leerer Wert
     } **/
}
