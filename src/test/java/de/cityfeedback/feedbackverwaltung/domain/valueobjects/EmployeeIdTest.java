package de.cityfeedback.feedbackverwaltung.domain.valueobjects;

import static org.junit.jupiter.api.Assertions.*;

import de.cityfeedback.feedbackverwaltung.domain.valueobject.EmployeeId;
import org.junit.jupiter.api.Test;

class EmployeeIdTest {
  @Test
  void EmployeeIdIsValid() {
    EmployeeId employeeId = new EmployeeId(1L);
    assertEquals(1L, employeeId.employeeId());
  }

  @Test
  void EmployeeIdThrowsExceptionWhenNull() {
    IllegalArgumentException exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> {
              new EmployeeId(null);
            });
    assertEquals("EmployeeId cannot be null", exception.getMessage());
  }

  @Test
  void EmployeeIdThrowsExceptionWhenNegative() {
    IllegalArgumentException exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> {
              new EmployeeId(-1L);
            });
    assertEquals("EmployeeId cannot be null", exception.getMessage());
  }

  @Test
  void EmployeeIdThrowsExceptionWhenZero() {
    IllegalArgumentException exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> {
              new EmployeeId(0L);
            });
    assertEquals("EmployeeId cannot be null", exception.getMessage());
  }
}
