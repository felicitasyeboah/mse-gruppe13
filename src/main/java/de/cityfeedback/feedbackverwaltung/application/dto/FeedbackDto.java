package de.cityfeedback.feedbackverwaltung.application.dto;

import de.cityfeedback.feedbackverwaltung.domain.model.Feedback;
import java.time.LocalDateTime;

public record FeedbackDto(
    Long id,
    String title,
    String content,
    String category,
    Long citizenId,
    Long employeeId,
    String comment,
    String status,
    LocalDateTime createdAt,
    LocalDateTime updatedAt) {

  public FeedbackDto(
      String title,
      String content,
      Long citizenId,
      String comment,
      String categoryName,
      Long employeeId) {
    this(null, title, content, categoryName, citizenId, employeeId, comment, null, null, null);
  }

  public static FeedbackDto fromFeedback(Feedback feedback) {
    return new FeedbackDto(
        feedback.getId(),
        feedback.getTitle(),
        feedback.getContent(),
        feedback.getCategory().getCategoryName(),
        feedback.getCitizenId().citizenId(),
        feedback.getEmployeeId() != null ? feedback.getEmployeeId().employeeId() : null,
        feedback.getComment(),
        feedback.getStatus().getStatusName(),
        feedback.getCreatedAt(),
        feedback.getUpdatedAt());
  }
}
