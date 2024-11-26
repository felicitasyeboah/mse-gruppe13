package de.cityfeedback.feedbackverwaltung.domain.valueobject;

import jakarta.persistence.Embeddable;

@Embeddable
public record EmployeeId(Long employeeId) {
  public EmployeeId {
    if (employeeId == null) {
      throw new IllegalArgumentException("EmployeeId cannot be null");
    }
  }
}