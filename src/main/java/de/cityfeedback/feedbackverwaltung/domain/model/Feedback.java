package de.cityfeedback.feedbackverwaltung.domain.model;

import de.cityfeedback.feedbackverwaltung.domain.valueobject.CitizenId;
import de.cityfeedback.feedbackverwaltung.domain.valueobject.EmployeeId;
import de.cityfeedback.feedbackverwaltung.domain.valueobject.FeedbackCategory;
import de.cityfeedback.feedbackverwaltung.domain.valueobject.FeedbackStatus;
import de.cityfeedback.shared.validator.Validation;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "FEEDBACK")
public class Feedback {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "FEEDBACK_ID")
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(name = "CATEGORY")
  private FeedbackCategory category;

  @Column(name = "TITLE", length = 1000)
  private String title;

  @Column(name = "CONTENT", columnDefinition = "TEXT")
  private String content;

  @Embedded
  @AttributeOverrides({
    @AttributeOverride(name = "citizenId", column = @Column(name = "CITIZEN_ID"))
  })
  private CitizenId citizenId;

  @Embedded
  @AttributeOverrides({
    @AttributeOverride(name = "employeeId", column = @Column(name = "EMPLOYEE_ID"))
  })
  private EmployeeId employeeId;

  @Column(name = "COMMENT", columnDefinition = "TEXT")
  private String comment;

  @Enumerated(EnumType.STRING)
  @Column(name = "STATUS")
  private FeedbackStatus status;

  @Column(name = "CREATED_AT")
  private LocalDateTime createdAt;

  @Column(name = "UPDATED_AT")
  private LocalDateTime updatedAt;

  // Constructor for creation
  public Feedback(String title, String content, String categoryName, CitizenId citizenId) {
    Validation.validateFeedbackContent(content);
    Validation.validateFeedbackTitle(title);
    Validation.validateFeedbackCategory(categoryName);
    this.title = title;
    this.content = content;
    this.citizenId = citizenId;
    this.status = FeedbackStatus.NEW; // Default status
    this.createdAt = LocalDateTime.now();
    this.category = FeedbackCategory.fromCategoryName(categoryName);
  }

  public Feedback() {
    this.status = FeedbackStatus.NEW;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Feedback feedback = (Feedback) o;
    return Objects.equals(title, feedback.title)
        && Objects.equals(content, feedback.content)
        && category == feedback.category
        && Objects.equals(citizenId, feedback.citizenId)
        && Objects.equals(employeeId, feedback.employeeId)
        && Objects.equals(comment, feedback.comment)
        && status == feedback.status;
  }

  @Override
  public int hashCode() {
    return Objects.hash(title, content, category, citizenId, employeeId, comment, status);
  }

  // Domain logic
  public void assignToEmployee(EmployeeId employeeId) {
    this.employeeId = employeeId;
    this.status = FeedbackStatus.IN_PROGRESS;
    this.updatedAt = LocalDateTime.now();
  }

  public void addComment(String comment) {
    this.comment = comment;
    Validation.validateComment(comment);
    this.status = FeedbackStatus.IN_PROGRESS;
    this.updatedAt = LocalDateTime.now();
  }

  public void closeFeedback() {
    this.status = FeedbackStatus.CLOSED;
    this.updatedAt = LocalDateTime.now();
  }

  // Other domain logic as needed
}
