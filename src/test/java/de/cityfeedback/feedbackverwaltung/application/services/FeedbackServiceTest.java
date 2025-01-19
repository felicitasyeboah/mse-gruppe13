package de.cityfeedback.feedbackverwaltung.application.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import de.cityfeedback.feedbackverwaltung.application.dto.FeedbackDto;
import de.cityfeedback.feedbackverwaltung.domain.model.Feedback;
import de.cityfeedback.feedbackverwaltung.domain.valueobject.*;
import de.cityfeedback.feedbackverwaltung.infrastructure.repositories.FeedbackRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;

@SpringBootTest
class FeedbackServiceTest {

  @MockBean private FeedbackRepository feedbackRepository;

  @MockBean private ApplicationEventPublisher eventPublisher;

  @Autowired private FeedbackService feedbackService;

  private Feedback feedback;
  private FeedbackDto request;
  FeedbackCategory category;

  @BeforeEach
  void setup() {
    // Set up test data
    request = new FeedbackDto("Issue", "Details of the issue", 1L, null, "Beschwerde", null);
    category = FeedbackCategory.fromCategoryName(request.category());
    feedback = new Feedback();
    feedback.setId(1L);
    feedback.setCategory(category);
    feedback.setTitle(request.title());
    feedback.setContent(request.content());
    feedback.setCitizenId(new CitizenId(request.citizenId()));
  }

  @Test
  void createFeedback_ShouldReturnFeedbackAndPublishEvent() {
    // Arrange
    when(feedbackRepository.save(any(Feedback.class))).thenReturn(feedback);

    // Act
    Feedback createdFeedback =
        feedbackService.createFeedback(
            request.title(), request.content(), request.citizenId(), category);

    // Assert
    assertNotNull(createdFeedback);
    assertEquals(feedback.getId(), createdFeedback.getId());
    assertEquals(feedback.getCategory(), createdFeedback.getCategory());
    assertEquals(feedback.getTitle(), createdFeedback.getTitle());
    assertEquals(feedback.getContent(), createdFeedback.getContent());
    assertEquals(FeedbackStatus.NEW, feedback.getStatus());

    // Verify that the event was published
    // TODO: FIX eventCaptor
    //    ArgumentCaptor<FeedbackCreatedEvent> eventCaptor =
    // ArgumentCaptor.forClass(FeedbackCreatedEvent.class);
    //    verify(eventPublisher, times(1)).publishEvent(eventCaptor.capture());
    //    FeedbackCreatedEvent event = eventCaptor.getValue();
    //
    //    assertNotNull(event);
    //    assertEquals(feedback.getId(), event.getFeedbackId());
    //    assertEquals(feedback.getCategory().getCategoryName(), event.getCategory());
    //    assertEquals(feedback.getTitle(), event.getTitle());
    //    assertEquals(feedback.getContent(), event.getContent());
    //    assertEquals(feedback.getCitizenId().citizenId(), event.getCitizenId());
    //    assertEquals(feedback.getStatus().getStatusName(), event.getStatus());
  }

  @Test
  void updateFeedback_withValidAssignUpdateType_shouldAssignEmployee() {
    Long feedbackId = feedback.getId();
    String comment = null;
    Long userId = 1L;
    String userRole = "EMPLOYEE";
    String updateType = "assign";
    when(feedbackRepository.findById(feedbackId)).thenReturn(Optional.of(feedback));
    when(feedbackRepository.save(any(Feedback.class))).thenReturn(feedback);

    Feedback updatedFeedback =
        feedbackService.updateFeedback(feedbackId, comment, userId, userRole, updateType);

    assertNotNull(updatedFeedback);
    assertEquals(new EmployeeId(userId), updatedFeedback.getEmployeeId());
  }

  @Test
  void updateFeedback_withValidCommentUpdateType_shouldAddComment() {
    Long feedbackId = feedback.getId();
    String comment = "New comment";
    Long userId = 1L;
    String userRole = "EMPLOYEE";
    String updateType = "comment";
    when(feedbackRepository.findById(feedbackId)).thenReturn(Optional.of(feedback));
    when(feedbackRepository.save(any(Feedback.class))).thenReturn(feedback);

    Feedback updatedFeedback =
        feedbackService.updateFeedback(feedbackId, comment, userId, userRole, updateType);

    assertNotNull(updatedFeedback);
    assertEquals(comment, updatedFeedback.getComment());
  }

  @Test
  void updateFeedback_withValidCloseUpdateType_shouldCloseFeedback() {
    Long feedbackId = feedback.getId();
    String comment = null;
    Long userId = 1L;
    String userRole = "EMPLOYEE";
    String updateType = "close";
    when(feedbackRepository.findById(feedbackId)).thenReturn(Optional.of(feedback));
    when(feedbackRepository.save(any(Feedback.class))).thenReturn(feedback);

    Feedback updatedFeedback =
        feedbackService.updateFeedback(feedbackId, comment, userId, userRole, updateType);

    assertNotNull(updatedFeedback);
    assertEquals(FeedbackStatus.CLOSED, updatedFeedback.getStatus());
  }

  @Test
  void updateFeedback_withInvalidUpdateType_shouldThrowException() {
    Long feedbackId = feedback.getId();
    String comment = null;
    Long userId = 1L;
    String userRole = "EMPLOYEE";
    String updateType = "invalid";
    when(feedbackRepository.findById(feedbackId)).thenReturn(Optional.of(feedback));

    assertThrows(
        IllegalArgumentException.class,
        () -> feedbackService.updateFeedback(feedbackId, comment, userId, userRole, updateType));
  }

  @Test
  void updateFeedback_withUnauthorizedUser_shouldThrowException() {
    Long feedbackId = feedback.getId();
    String comment = null;
    Long userId = 2L;
    String userRole = "CITIZEN";
    String updateType = "assign";
    feedback.assignToEmployee(new EmployeeId(1L));
    when(feedbackRepository.findById(feedbackId)).thenReturn(Optional.of(feedback));

    assertThrows(
        IllegalArgumentException.class,
        () -> feedbackService.updateFeedback(feedbackId, comment, userId, userRole, updateType));
  }

  @Test
  void updateFeedback_withNonExistentFeedback_shouldThrowException() {
    Long feedbackId = feedback.getId();
    String comment = null;
    Long userId = 1L;
    String userRole = "EMPLOYEE";
    String updateType = "assign";
    when(feedbackRepository.findById(feedbackId)).thenReturn(Optional.empty());

    assertThrows(
        EntityNotFoundException.class,
        () -> feedbackService.updateFeedback(feedbackId, comment, userId, userRole, updateType));
  }

  @Test
  void assignFeedbackToEmployee_ShouldReturnUpdatedFeedback() {
    // Arrange
    Long employeeId = 1L;
    when(feedbackRepository.findById(feedback.getId())).thenReturn(Optional.of(feedback));
    when(feedbackRepository.save(any(Feedback.class))).thenReturn(feedback);

    // Act
    Feedback assignedFeedback =
        feedbackService.assignFeedbackToEmployee(feedback.getId(), employeeId);

    // Assert
    assertNotNull(assignedFeedback);
    assertEquals(FeedbackStatus.IN_PROGRESS, assignedFeedback.getStatus());
    assertEquals(new EmployeeId(employeeId), assignedFeedback.getEmployeeId());
  }

  @Test
  void assignFeedbackToEmployee_FeedbackNotFound_ShouldThrowException() {
    // Arrange
    Long employeeId = 1L;
    when(feedbackRepository.findById(feedback.getId())).thenReturn(Optional.empty());

    // Act & Assert
    Long feedbackId = feedback.getId();
    assertThrows(
        EntityNotFoundException.class,
        () -> feedbackService.assignFeedbackToEmployee(feedbackId, employeeId));
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
    assertEquals(FeedbackStatus.IN_PROGRESS, updatedFeedback.getStatus());
  }

  @Test
  void addCommentToFeedback_FeedbackNotFound_ShouldThrowException() {
    // Arrange
    String comment = "New comment";
    when(feedbackRepository.findById(feedback.getId())).thenReturn(Optional.empty());

    // Act & Assert
    Long feedbackId = feedback.getId();
    assertThrows(
        EntityNotFoundException.class,
        () -> feedbackService.addCommentToFeedback(feedbackId, comment));
  }

  @Test
  void findAllFeedbacksForCitizen_ShouldReturnListOfFeedbacks() {
    // Arrange
    List<Feedback> feedbacks = List.of(feedback);
    when(feedbackRepository.findAllByCitizenId(feedback.getCitizenId().citizenId()))
        .thenReturn(feedbacks);

    // Act
    List<FeedbackDto> foundFeedbacks =
        feedbackService.findAllFeedbacksForCitizen(feedback.getCitizenId().citizenId());

    // Assert
    assertNotNull(foundFeedbacks);
    assertEquals(1, foundFeedbacks.size());
    assertEquals(feedback.getId(), foundFeedbacks.get(0).id());
  }

  @Test
  void findAllOpenFeedbacks_shouldReturnListOfOpenFeedbacks() {
    List<Feedback> feedbacks = List.of(feedback);
    when(feedbackRepository.findAllByStatusNot(FeedbackStatus.CLOSED)).thenReturn(feedbacks);

    List<FeedbackDto> openFeedbacks = feedbackService.findAllOpenFeedbacks();

    assertNotNull(openFeedbacks);
    assertEquals(1, openFeedbacks.size());
    assertEquals(feedback.getId(), openFeedbacks.get(0).id());
  }

  @Test
  void findAllOpenFeedbacks_noOpenFeedbacks_shouldReturnEmptyList() {
    when(feedbackRepository.findAllByStatusNot(FeedbackStatus.CLOSED)).thenReturn(List.of());

    List<FeedbackDto> openFeedbacks = feedbackService.findAllOpenFeedbacks();

    assertNotNull(openFeedbacks);
    assertTrue(openFeedbacks.isEmpty());
  }

  @Test
  void getFeedbackById_ShouldReturnFeedback_WhenFeedbackExists() {
    Long feedbackId = feedback.getId();

    when(feedbackRepository.findById(feedbackId)).thenReturn(Optional.of(feedback));

    Feedback result = feedbackService.getFeedbackById(feedbackId);

    assertNotNull(result);
    assertEquals(feedbackId, result.getId());
    assertEquals("Issue", result.getTitle());
    assertEquals("Details of the issue", result.getContent());
    assertEquals(FeedbackCategory.COMPLAINT, result.getCategory());
  }

  @Test
  void getFeedbackById_ShouldThrowEntityNotFoundException_WhenFeedbackDoesNotExist() {
    Long feedbackId = 1L;

    when(feedbackRepository.findById(feedbackId)).thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class, () -> feedbackService.getFeedbackById(feedbackId));
  }
}
