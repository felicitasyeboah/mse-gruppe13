package de.cityfeedback.feedbackverwaltung.domain.events;

import de.cityfeedback.shared.events.DomainEvent;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class FeedbackCreatedEvent extends DomainEvent {
  Long feedbackId;
  String category;
  String title;
  String content;
  Long citizenId;
  String status;
  LocalDateTime createdAt;
}
