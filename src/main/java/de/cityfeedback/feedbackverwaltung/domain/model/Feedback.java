package de.cityfeedback.feedbackverwaltung.domain.model;

import de.cityfeedback.feedbackverwaltung.domain.valueobject.CitizenId;
import de.cityfeedback.feedbackverwaltung.domain.valueobject.EmployeeId;
import de.cityfeedback.feedbackverwaltung.domain.valueobject.FeedbackCategory;
import de.cityfeedback.feedbackverwaltung.domain.valueobject.FeedbackStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
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

  // Getters and Setters

  // Constructor for creation
  public Feedback(String title, String content, FeedbackCategory category, CitizenId citizenId) {
    this.title = title;
    this.content = content;
    this.category = category;
    this.citizenId = citizenId;
    this.status = FeedbackStatus.NEW; // Default status
    this.createdAt = LocalDateTime.now();
  }

  // Business methods
  public void assignToEmployee(EmployeeId employeeId) {
    this.employeeId = employeeId;
    this.status = FeedbackStatus.IN_PROGRESS;
    this.updatedAt = LocalDateTime.now();
  }

  public void addComment(String comment) {
    this.comment = comment;
    this.updatedAt = LocalDateTime.now();
  }

  public void updateStatus(FeedbackStatus status) {
    this.status = status;
    this.updatedAt = LocalDateTime.now();
  }

  public void closeFeedback(String comment) {
    this.comment = comment;
    this.status = FeedbackStatus.CLOSED;
    this.updatedAt = LocalDateTime.now();
  }

  // Other domain logic as needed
}
