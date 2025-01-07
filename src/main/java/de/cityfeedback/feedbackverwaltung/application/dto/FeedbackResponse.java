package de.cityfeedback.feedbackverwaltung.application.dto;

import de.cityfeedback.feedbackverwaltung.domain.model.Feedback;

import java.time.LocalDateTime;

public record FeedbackResponse(
    Long id,
    String title,
    String content,
    String categoryName,
    Long citizenId,
    Long employeeId,
    String comment,
    String status,
    LocalDateTime createdAt,
    LocalDateTime updatedAt) {

  public static FeedbackResponse fromFeedback(Feedback feedback) {
    return new FeedbackResponse(
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
