package de.cityfeedback.feedbackverwaltung.application.services;

import static org.junit.jupiter.api.Assertions.*;

import de.cityfeedback.feedbackverwaltung.domain.model.Feedback;
import de.cityfeedback.feedbackverwaltung.domain.valueobject.*;
import de.cityfeedback.feedbackverwaltung.infrastructure.repositories.FeedbackRepository;
import de.cityfeedback.validator.Validation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FeedbackServiceTest {

  @Autowired private FeedbackService feedbackService;

  @Autowired private FeedbackRepository feedbackRepository;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    feedbackService = new FeedbackService(feedbackRepository);
  }

  @Test
  void createFeedback_ShouldSaveAndReturnFeedback() {
    // Arrange
    String title = "Test Feedback";
    String content = "This is a test feedback content.";
    Long citizenId = 1L;

    // Act
    Feedback feedback =
        feedbackService.createFeedback(title, content, citizenId, FeedbackCategory.COMPLAINT);

    // Assert
    assertNotNull(feedback);
    assertEquals(title, feedback.getTitle());
    assertEquals(content, feedback.getContent());
    assertEquals(citizenId, feedback.getCitizenId().citizenId());
    assertEquals(FeedbackCategory.COMPLAINT, feedback.getCategory());
    assertEquals(FeedbackStatus.NEW, feedback.getStatus());

    // Verify that the feedback was saved to the database
    Feedback savedFeedback = feedbackRepository.findById(feedback.getId()).orElseThrow();
    assertEquals(feedback, savedFeedback);
  }

  @Test
  public void testUpdateFeedbackStatus_ShouldUpdateFeedbackStatusToInBearbeitung() {
    // arrange
    Feedback currentFeedback = feedbackRepository.findById(1L).orElseThrow();

    FeedbackStatus status = FeedbackStatus.IN_PROGRESS;

    // act
    Feedback updatedFeedback =
        feedbackService.updateFeedbackStatus(currentFeedback.getId(), status);
    // assert
    assertNotNull(updatedFeedback);
    assertEquals(status, updatedFeedback.getStatus());
  }

  @Test
  public void testAssignEmployeeToFeedback_ShouldAssignEmployee() {
    // arrange
    Feedback currentFeedback = feedbackRepository.findById(1L).orElseThrow();
    Long employeeId = 10L;
    // act
    Feedback updatedFeedback =
        feedbackService.assignFeedbackToEmployee(currentFeedback.getId(), employeeId);

    // assert
    assertNotNull(updatedFeedback);
    assertEquals(employeeId, updatedFeedback.getEmployeeId().employeeId());
  }

  @Test
  public void testAddCommentToFeedback_ShouldAddComment() {
    String comment = "das ist ein Kommentar von einem Mitarbeiter";
    Validation.validateComment(comment);
    Feedback currentFeedback = feedbackRepository.findById(1L).orElseThrow();
    Feedback updatedFeedback =
        feedbackService.addCommentToFeedback(currentFeedback.getId(), comment);
    assertNotNull(updatedFeedback);
  }
}
