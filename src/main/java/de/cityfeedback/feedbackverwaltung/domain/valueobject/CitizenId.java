package de.cityfeedback.feedbackverwaltung.domain.valueobject;

import jakarta.persistence.Embeddable;

@Embeddable
public record CitizenId(Long citizenId) {
  public CitizenId {
    if (citizenId == null) {
      throw new IllegalArgumentException("CitizenId cannot be null");
    }
  }
}