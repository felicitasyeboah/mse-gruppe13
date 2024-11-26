package de.cityfeedback.feedbackverwaltung.domain.valueobject;

import de.cityfeedback.validator.Validation;
import jakarta.persistence.Embeddable;

@Embeddable
public record Title(String title) {
  public Title {
    Validation.validateComplaintTitle(title);
  }
}
