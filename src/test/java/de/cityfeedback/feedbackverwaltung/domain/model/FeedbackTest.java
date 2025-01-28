package de.cityfeedback.feedbackverwaltung.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import de.cityfeedback.feedbackverwaltung.domain.valueobject.CitizenId;
import de.cityfeedback.feedbackverwaltung.domain.valueobject.EmployeeId;
import de.cityfeedback.feedbackverwaltung.domain.valueobject.FeedbackCategory;
import de.cityfeedback.feedbackverwaltung.domain.valueobject.FeedbackStatus;
import de.cityfeedback.shared.exception.WrongUserInputException;
import org.junit.jupiter.api.Test;

class FeedbackTest {

  @Test
  void feedbackCreationWithValidData() {
    CitizenId citizenId = new CitizenId(1L);
    Feedback feedback =
        new Feedback(
            "Title",
            "Content of the request",
            FeedbackCategory.REQUEST.getCategoryName(),
            citizenId);

    assertEquals("Title", feedback.getTitle());
    assertEquals("Content of the request", feedback.getContent());
    assertEquals(
        FeedbackCategory.REQUEST.getCategoryName(), feedback.getCategory().getCategoryName());
    assertEquals(citizenId, feedback.getCitizenId());
    assertEquals(FeedbackStatus.NEW, feedback.getStatus());
    assertNotNull(feedback.getCreatedAt());
  }

  @Test
  void feedbackCreationWithEmptyTitleThrowsException() {
    CitizenId citizenId = new CitizenId(1L);
    assertThrows(
        WrongUserInputException.class,
        () -> new Feedback("", "Content", FeedbackCategory.REQUEST.getCategoryName(), citizenId));
  }

  @Test
  void feedbackCreationWithInvalidTitleThrowsException() {
    CitizenId citizenId = new CitizenId(1L);
    assertThrows(
        WrongUserInputException.class,
        () -> new Feedback("xy", "Content", FeedbackCategory.REQUEST.getCategoryName(), citizenId));
  }

  @Test
  void feedbackCreationWithEmptyOrInvalidContentThrowsException() {
    CitizenId citizenId = new CitizenId(1L);
    assertThrows(
        WrongUserInputException.class,
        () -> new Feedback("Title", "", FeedbackCategory.REQUEST.getCategoryName(), citizenId));
    assertThrows(
        WrongUserInputException.class,
        () -> new Feedback("Title", "xyz", FeedbackCategory.REQUEST.getCategoryName(), citizenId));
  }

  @Test
  void assignToEmployeeUpdatesStatusAndTimestamp() {
    Feedback feedback = new Feedback();
    EmployeeId employeeId = new EmployeeId(1L);
    feedback.assignToEmployee(employeeId);

    assertEquals(employeeId, feedback.getEmployeeId());
    assertEquals(FeedbackStatus.IN_PROGRESS, feedback.getStatus());
    assertNotNull(feedback.getUpdatedAt());
  }

  @Test
  void addCommentUpdatesCommentAndTimestamp() {
    Feedback feedback = new Feedback();
    feedback.addComment("New comment");

    assertEquals("New comment", feedback.getComment());
    assertNotNull(feedback.getUpdatedAt());
  }

  @Test
  void closeFeedbackUpdatesCommentStatusAndTimestamp() {
    Feedback feedback = new Feedback();
    feedback.closeFeedback();

    assertEquals(FeedbackStatus.CLOSED, feedback.getStatus());
    assertNotNull(feedback.getUpdatedAt());
  }

  @Test
  void feedbackEqualityBasedOnRelevantFields() {
    Feedback feedback1 =
        new Feedback(
            "Title",
            "Content mit mindestens 10 Zeichen",
            FeedbackCategory.REQUEST.getCategoryName(),
            new CitizenId(1L));
    Feedback feedback2 =
        new Feedback(
            "Title",
            "Content mit mindestens 10 Zeichen",
            FeedbackCategory.REQUEST.getCategoryName(),
            new CitizenId(1L));

    assertEquals(feedback1, feedback2);
  }

  @Test
  void feedbackInequalityBasedOnDifferentFields() {
    Feedback feedback1 =
        new Feedback(
            "Title1",
            "Content mit mindestens 10 Zeichen",
            FeedbackCategory.REQUEST.getCategoryName(),
            new CitizenId(1L));
    Feedback feedback2 =
        new Feedback(
            "Title2",
            "Content mit mindestens 10 Zeichen",
            FeedbackCategory.REQUEST.getCategoryName(),
            new CitizenId(1L));

    assertNotEquals(feedback1, feedback2);
  }
}
