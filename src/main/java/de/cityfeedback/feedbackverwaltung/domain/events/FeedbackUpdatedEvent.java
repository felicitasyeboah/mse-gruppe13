package de.cityfeedback.feedbackverwaltung.domain.events;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FeedbackUpdatedEvent {
  public final long feednackId;
  public final Instant updatedAt;
}
