package de.cityfeedback.feedbackverwaltung.domain.valueobjects;

import de.cityfeedback.feedbackverwaltung.domain.valueobject.CitizenId;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CitizenIdTest {
  @Test
  void CitizenIdIsValid() {
    CitizenId citizenId = new CitizenId(1L);
    assertEquals(1L, citizenId.citizenId());
  }

  @Test
  void CitizenIdThrowsExceptionWhenNull() {
    IllegalArgumentException exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> {
              new CitizenId(null);
            });
    assertEquals("CitizenId cannot be null or negative", exception.getMessage());
  }

  @Test
  void CitizenIdThrowsExceptionWhenNegative() {
    IllegalArgumentException exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> {
              new CitizenId(-1L);
            });
    assertEquals("CitizenId cannot be null or negative", exception.getMessage());
  }

  @Test
  void CitizenIdThrowsExceptionWhenZero() {
    IllegalArgumentException exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> {
              new CitizenId(0L);
            });
    assertEquals("CitizenId cannot be null or negative", exception.getMessage());
  }
}
