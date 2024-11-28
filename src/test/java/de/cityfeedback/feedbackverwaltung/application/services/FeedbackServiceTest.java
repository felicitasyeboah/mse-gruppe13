package de.cityfeedback.feedbackverwaltung.application.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import de.cityfeedback.feedbackverwaltung.domain.events.FeedbackCreatedEvent;
import de.cityfeedback.feedbackverwaltung.domain.model.Feedback;
import de.cityfeedback.feedbackverwaltung.domain.valueobject.*;
import de.cityfeedback.feedbackverwaltung.infrastructure.repositories.FeedbackRepository;
import de.cityfeedback.feedbackverwaltung.ui.controller.FeedbackRequest;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;

@SpringBootTest
class FeedbackServiceTest {

  @MockBean
  private FeedbackRepository feedbackRepository;

  @MockBean
  private ApplicationEventPublisher eventPublisher;

  @Autowired
  private FeedbackService feedbackService;

  private Feedback feedback;
  private FeedbackRequest request;

  @BeforeEach
  void setup() {
    // Set up test data
    request = new FeedbackRequest("Issue", "Details of the issue", 1L, "Beschwerde");
    feedback = new Feedback();
    feedback.setId(1L);
    feedback.setCategory(FeedbackCategory.COMPLAINT);
    feedback.setTitle(request.title());
    feedback.setContent(request.content());
    feedback.setCitizenId(new CitizenId(request.citizenId()));
  }

  @Test
  void createFeedback_ShouldReturnFeedbackAndPublishEvent() {
    // Arrange
    when(feedbackRepository.save(any(Feedback.class))).thenReturn(feedback);

    // Act
    Feedback createdFeedback = feedbackService.createFeedback(request.title(), request.content(), request.citizenId(), FeedbackCategory.COMPLAINT);

    // Assert
    assertNotNull(createdFeedback);
    assertEquals(feedback.getId(), createdFeedback.getId());
    assertEquals(feedback.getCategory(), createdFeedback.getCategory());
    assertEquals(feedback.getTitle(), createdFeedback.getTitle());
    assertEquals(feedback.getContent(), createdFeedback.getContent());

    // Verify that the event was published
    ArgumentCaptor<FeedbackCreatedEvent> eventCaptor = ArgumentCaptor.forClass(FeedbackCreatedEvent.class);
    verify(eventPublisher, times(1)).publishEvent(eventCaptor.capture());
    FeedbackCreatedEvent event = eventCaptor.getValue();

    assertNotNull(event);
    assertEquals(feedback.getId(), event.getFeedbackId());
    assertEquals(feedback.getCategory().getCategoryName(), event.getCategory());
    assertEquals(feedback.getTitle(), event.getTitle());
    assertEquals(feedback.getContent(), event.getContent());
    assertEquals(feedback.getCitizenId().citizenId(), event.getCitizenId());
    assertEquals(feedback.getStatus().getStatusName(), event.getStatus());
  }

  @Test
  void updateFeedbackStatus_ShouldReturnUpdatedFeedback() {
    // Arrange
    FeedbackStatus status = FeedbackStatus.IN_PROGRESS;
    when(feedbackRepository.findById(feedback.getId())).thenReturn(Optional.of(feedback));
    when(feedbackRepository.save(any(Feedback.class))).thenReturn(feedback);

    // Act
    Feedback updatedFeedback = feedbackService.updateFeedbackStatus(feedback.getId(), status);

    // Assert
    assertNotNull(updatedFeedback);
    assertEquals(status, updatedFeedback.getStatus());
  }

  @Test
  void updateFeedbackStatus_FeedbackNotFound_ShouldThrowException() {
    // Arrange
    when(feedbackRepository.findById(feedback.getId())).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(EntityNotFoundException.class, () -> feedbackService.updateFeedbackStatus(feedback.getId(), FeedbackStatus.IN_PROGRESS));
  }

  @Test
  void assignFeedbackToEmployee_ShouldReturnUpdatedFeedback() {
    // Arrange
    Long employeeId = 1L;
    when(feedbackRepository.findById(feedback.getId())).thenReturn(Optional.of(feedback));
    when(feedbackRepository.save(any(Feedback.class))).thenReturn(feedback);

    // Act
    Feedback assignedFeedback = feedbackService.assignFeedbackToEmployee(feedback.getId(), employeeId);

    // Assert
    assertNotNull(assignedFeedback);
    assertEquals(new EmployeeId(employeeId), assignedFeedback.getEmployeeId());
  }

  @Test
  void assignFeedbackToEmployee_FeedbackNotFound_ShouldThrowException() {
    // Arrange
    Long employeeId = 1L;
    when(feedbackRepository.findById(feedback.getId())).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(EntityNotFoundException.class, () -> feedbackService.assignFeedbackToEmployee(feedback.getId(), employeeId));
  }

  @Test
  void addCommentToFeedback_ShouldReturnUpdatedFeedback() {
    // Arrange
    String comment = "New comment";
    when(feedbackRepository.findById(feedback.getId())).thenReturn(Optional.of(feedback));
    when(feedbackRepository.save(any(Feedback.class))).thenReturn(feedback);

    // Act
    Feedback updatedFeedback = feedbackService.addCommentToFeedback(feedback.getId(), comment);

    // Assert
    assertNotNull(updatedFeedback);
    assertEquals(comment, updatedFeedback.getComment());
  }

  @Test
  void addCommentToFeedback_FeedbackNotFound_ShouldThrowException() {
    // Arrange
    String comment = "New comment";
    when(feedbackRepository.findById(feedback.getId())).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(EntityNotFoundException.class, () -> feedbackService.addCommentToFeedback(feedback.getId(), comment));
  }

  @Test
  void findAllFeedbacksForCitizen_ShouldReturnListOfFeedbacks() {
    // Arrange
    List<Feedback> feedbacks = List.of(feedback);
    when(feedbackRepository.findAllByCitizenId(feedback.getCitizenId().citizenId())).thenReturn(feedbacks);

    // Act
    List<Feedback> foundFeedbacks = feedbackService.findAllFeedbacksForCitizen(feedback.getCitizenId().citizenId());

    // Assert
    assertNotNull(foundFeedbacks);
    assertEquals(1, foundFeedbacks.size());
    assertEquals(feedback.getId(), foundFeedbacks.get(0).getId());
  }
}
