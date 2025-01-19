package de.cityfeedback.feedbackverwaltung.domain.events;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FeedbackUpdatedEvent {
  public final long feedbackId;
  public final LocalDateTime updatedAt;
  public final String statusName;
}
