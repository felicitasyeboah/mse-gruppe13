package de.cityfeedback.shared.events;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FeedbackUpdatedEvent extends DomainEvent {
  public final long feedbackId;
  public final long citizenId;
  public final long employeeId;
  public final String title;
  public final LocalDateTime updatedAt;
  public final String statusName;
}
