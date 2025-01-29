package de.cityfeedback.feedbackverwaltung.domain.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import de.cityfeedback.feedbackverwaltung.domain.valueobject.CitizenId;
import de.cityfeedback.feedbackverwaltung.domain.valueobject.EmployeeId;
import de.cityfeedback.feedbackverwaltung.domain.valueobject.FeedbackCategory;
import de.cityfeedback.feedbackverwaltung.domain.valueobject.FeedbackStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FeedbackTest {
  private Feedback feedback;
  private EmployeeId mockEmployeeId;

  @BeforeEach
  void setUp() {
    feedback = new Feedback();
    mockEmployeeId = mock(EmployeeId.class);
  }

  @Test
  void assignToEmployee_ShouldThrowException_WhenAlreadyAssignedToSameEmployee() {
    feedback.setEmployeeId(new EmployeeId(1L));
    when(mockEmployeeId.employeeId()).thenReturn(1L);

    IllegalArgumentException exception =
        assertThrows(IllegalArgumentException.class, () -> feedback.assignToEmployee(1L));
    assertEquals("Feedback is already assigned to you", exception.getMessage());
  }

  @Test
  void assignToEmployee_ShouldAssign_WhenValid() {
    feedback.setEmployeeId(new EmployeeId(2L));
    feedback.assignToEmployee(1L);

    assertEquals(1L, feedback.getEmployeeId().employeeId());
    assertEquals(FeedbackStatus.IN_PROGRESS, feedback.getStatus());
    assertNotNull(feedback.getUpdatedAt());
  }

  @Test
  void addComment_ShouldThrowException_WhenFeedbackNotAssigned() {
    IllegalArgumentException exception =
        assertThrows(IllegalArgumentException.class, () -> feedback.addComment("Test Comment", 1L));
    assertEquals("Please assign first to the feedback", exception.getMessage());
  }

  @Test
  void addComment_ShouldThrowException_WhenEmployeeNotAssigned() {
    feedback.setEmployeeId(new EmployeeId(2L));

    IllegalArgumentException exception =
        assertThrows(IllegalArgumentException.class, () -> feedback.addComment("Test Comment", 1L));
    assertEquals(
        "Unauthorized to update this feedback. You are not the assigned employee. Please assign first.",
        exception.getMessage());
  }

  @Test
  void addComment_ShouldAddComment_WhenValid() {
    feedback.setEmployeeId(new EmployeeId(1L));
    feedback.addComment("Test Comment", 1L);

    assertEquals("Test Comment", feedback.getComment());
    assertEquals(FeedbackStatus.IN_PROGRESS, feedback.getStatus());
    assertNotNull(feedback.getUpdatedAt());
  }

  @Test
  void closeFeedback_ShouldThrowException_WhenFeedbackNotAssigned() {
    IllegalArgumentException exception =
        assertThrows(IllegalArgumentException.class, () -> feedback.closeFeedback(1L));
    assertEquals("Please assign first to the feedback", exception.getMessage());
  }

  @Test
  void closeFeedback_ShouldThrowException_WhenEmployeeNotAssigned() {
    feedback.setEmployeeId(new EmployeeId(2L));

    IllegalArgumentException exception =
        assertThrows(IllegalArgumentException.class, () -> feedback.closeFeedback(1L));
    assertEquals(
        "Unauthorized to update this feedback. You are not the assigned employee. Please assign first.",
        exception.getMessage());
  }

  @Test
  void closeFeedback_ShouldCloseFeedback_WhenValid() {
    feedback.setEmployeeId(new EmployeeId(1L));
    feedback.closeFeedback(1L);

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
