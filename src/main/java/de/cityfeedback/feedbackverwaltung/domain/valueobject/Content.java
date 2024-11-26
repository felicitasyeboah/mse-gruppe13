package de.cityfeedback.feedbackverwaltung.domain.valueobject;

import de.cityfeedback.validator.Validation;
import jakarta.persistence.Embeddable;

@Embeddable
public record Content(String content) {
  public Content {
    Validation.validateComplaintContent(content);
  }
}
