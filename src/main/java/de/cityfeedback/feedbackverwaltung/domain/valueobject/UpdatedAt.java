package de.cityfeedback.feedbackverwaltung.domain.valueobject;

import jakarta.persistence.Embeddable;
import java.time.Instant;

@Embeddable
public record UpdatedAt(Instant updatedAt) {
  public UpdatedAt {
    if (updatedAt == null) {
      throw new IllegalArgumentException("Date cannot be null.");
    }
  }
}
