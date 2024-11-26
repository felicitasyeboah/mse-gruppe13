package de.cityfeedback.feedbackverwaltung.domain.valueobject;

import de.cityfeedback.validator.Validation;
import jakarta.persistence.Embeddable;

@Embeddable
public record Comment(String comment) {
  public Comment {
    Validation.validateComment(comment);
  }
}
