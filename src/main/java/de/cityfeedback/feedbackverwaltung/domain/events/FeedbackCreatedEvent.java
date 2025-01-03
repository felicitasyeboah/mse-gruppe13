package de.cityfeedback.feedbackverwaltung.domain.events;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@ToString
public class FeedbackCreatedEvent {
  Long feedbackId;
  String category;
  String title;
  String content;
  Long citizenId;
  String status;
  LocalDateTime createdAt;
}
