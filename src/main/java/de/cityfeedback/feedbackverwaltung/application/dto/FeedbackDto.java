package de.cityfeedback.feedbackverwaltung.application.dto;

import de.cityfeedback.feedbackverwaltung.domain.model.Feedback;
import java.time.LocalDateTime;

public record FeedbackDto(
    Long id,
    String title,
    String content,
    String category,
    Long citizenId,
    String citizenName,
    String citizenEmail,
    Long employeeId,
    String employeeName,
    String employeeEmail,
    String comment,
    String status,
    LocalDateTime createdAt,
    LocalDateTime updatedAt) {

  public FeedbackDto(String title, String content, Long citizenId, String categoryName) {
    this(
        null,
        title,
        content,
        categoryName,
        citizenId,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null);
  }

  public static FeedbackDto of(Feedback feedback, UserDto citizen, UserDto employee) {
    return new FeedbackDto(
        feedback.getId(),
        feedback.getTitle(),
        feedback.getContent(),
        feedback.getCategory().getCategoryName(),
        feedback.getCitizenId().citizenId(),
        citizen != null ? citizen.getUserName() : null,
        citizen != null ? citizen.getEmail() : null,
        feedback.getEmployeeId() != null ? feedback.getEmployeeId().employeeId() : null,
        feedback.getEmployeeId() != null && employee != null ? employee.getUserName() : null,
        feedback.getEmployeeId() != null && employee != null ? employee.getEmail() : null,
        feedback.getComment(),
        feedback.getStatus().getStatusName(),
        feedback.getCreatedAt(),
        feedback.getUpdatedAt());
  }
}
