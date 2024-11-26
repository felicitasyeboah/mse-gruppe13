package de.cityfeedback.feedbackverwaltung.domain.model;

import de.cityfeedback.feedbackverwaltung.domain.valueobject.*;
import jakarta.persistence.*;
import java.util.Date;
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
  private CategoryEnum category;

  @Enumerated(EnumType.STRING)
  private StatusEnum status;

  @Embedded private Title title;
  @Embedded private Content content;

  @Embedded
  @Column(name = "CITIZEN_ID")
  private CitizenId citizenId;

  @Embedded
  @Column(name = "EMPLOYEE_ID")
  private EmployeeId employeeId;

  @Embedded private Comment comment;

  @Column(name = "CREATED_AT", insertable = false, updatable = false)
  @Embedded
  private CreatedAt createdAt;

  @Column(name = "UPDATED_AT", insertable = false, updatable = false)
  @Embedded
  private UpdatedAt updatedAt;

  public Feedback() {}

  public Feedback(CategoryEnum category, Title title, Content content, CitizenId citizenId) {
    this.category = category;
    this.title = title;
    this.content = content;
    this.citizenId = citizenId;
    this.status = StatusEnum.NEW;
    this.createdAt = new CreatedAt(new Date().toInstant());
    this.updatedAt = new UpdatedAt(new Date().toInstant());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Feedback feedback = (Feedback) o;
    return Objects.equals(id, feedback.id)
        && Objects.equals(category, feedback.category)
        && Objects.equals(title, feedback.title)
        && Objects.equals(content, feedback.content)
        && Objects.equals(citizenId, feedback.citizenId)
        && Objects.equals(employeeId, feedback.employeeId)
        && Objects.equals(comment, feedback.comment)
        && Objects.equals(status, feedback.status)
        && Objects.equals(createdAt, feedback.createdAt)
        && Objects.equals(updatedAt, feedback.updatedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        id, category, title, content, citizenId, employeeId, comment, status, createdAt, updatedAt);
  }

  @Override
  public String toString() {
    return "Feedback{"
        + "id="
        + id
        + ", categoryId="
        + category
        + ", title='"
        + title
        + '\''
        + ", content='"
        + content
        + '\''
        + ", citizenId="
        + citizenId
        + ", employeeId="
        + employeeId
        + ", comment='"
        + comment
        + '\''
        + ", statusId="
        + status
        + ", createdAt="
        + createdAt
        + ", updatedAt="
        + updatedAt
        + '}';
  }
}
