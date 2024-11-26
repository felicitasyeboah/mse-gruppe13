package de.cityfeedback.feedbackverwaltung.domain.valueobject;

import jakarta.persistence.Embeddable;
import java.time.Instant;

@Embeddable
public record CreatedAt(Instant createDate) {
  public CreatedAt {
    if (createDate == null) {
      throw new IllegalArgumentException("Date cannot be null.");
    }
  }
}
