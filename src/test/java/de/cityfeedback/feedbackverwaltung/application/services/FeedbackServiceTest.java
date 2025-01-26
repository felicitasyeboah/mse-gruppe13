package de.cityfeedback.feedbackverwaltung.application.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import de.cityfeedback.feedbackverwaltung.application.dto.FeedbackDto;
import de.cityfeedback.feedbackverwaltung.application.dto.UserDto;
import de.cityfeedback.feedbackverwaltung.domain.model.Feedback;
import de.cityfeedback.feedbackverwaltung.domain.valueobject.CitizenId;
import de.cityfeedback.feedbackverwaltung.domain.valueobject.EmployeeId;
import de.cityfeedback.feedbackverwaltung.domain.valueobject.FeedbackCategory;
import de.cityfeedback.feedbackverwaltung.domain.valueobject.FeedbackStatus;
import de.cityfeedback.feedbackverwaltung.events.FeedbackCreatedEvent;
import de.cityfeedback.feedbackverwaltung.infrastructure.repositories.FeedbackRepository;
import de.cityfeedback.shared.events.FeedbackUpdatedEvent;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;

class FeedbackServiceTest {

  @Mock private FeedbackRepository feedbackRepository;

  @Mock private ApplicationEventPublisher eventPublisher;

  @Mock private UserServiceClient userServiceClient;

  @InjectMocks private FeedbackService feedbackService;

  @BeforeEach()
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void createFeedback_ShouldPublishFeedbackCreatedEvent() {
    String title = "Test Title";
    String content = "Test Content";
    Long citizenId = 123L;
    FeedbackCategory category = FeedbackCategory.COMPLAINT;

    Feedback mockFeedback = new Feedback(title, content, category, new CitizenId(citizenId));

    UserDto mockUserDto = new UserDto(citizenId, "John Doe", "john.doe@example.com", "CITIZEN");

    when(feedbackRepository.save(any(Feedback.class))).thenReturn(mockFeedback);
    when(userServiceClient.fetchUserById(citizenId)).thenReturn(mockUserDto);

    FeedbackDto result = feedbackService.createFeedback(title, content, citizenId, category);

    assertNotNull(result);
    assertEquals(mockFeedback.getStatus().getStatusName(), result.status());
    assertEquals(mockFeedback.getTitle(), result.title());
    assertEquals(mockFeedback.getContent(), result.content());
    assertEquals(mockUserDto.getUserId(), result.citizenId());

    verify(feedbackRepository, times(1)).save(any(Feedback.class));

    ArgumentCaptor<FeedbackCreatedEvent> eventCaptor =
        ArgumentCaptor.forClass(FeedbackCreatedEvent.class);
    verify(eventPublisher, times(1)).publishEvent(eventCaptor.capture());
    FeedbackCreatedEvent publishedEvent = eventCaptor.getValue();
    assertNotNull(publishedEvent);
    assertEquals(mockFeedback.getId(), publishedEvent.getFeedbackId());

    verify(userServiceClient, times(1)).fetchUserById(citizenId);
  }

  @Test
  void assignFeedbackToEmployee_ShouldAssignAndSaveFeedback() {
    Long feedbackId = 1L;
    Long employeeId = 456L;

    Feedback mockFeedback =
        new Feedback("Test Title", "Test Content", FeedbackCategory.COMPLAINT, new CitizenId(123L));

    when(feedbackRepository.findById(feedbackId)).thenReturn(Optional.of(mockFeedback));
    when(feedbackRepository.save(any(Feedback.class))).thenReturn(mockFeedback);

    Feedback result = feedbackService.assignFeedbackToEmployee(feedbackId, employeeId);

    assertNotNull(result);
    assertEquals(employeeId, result.getEmployeeId().employeeId());

    verify(feedbackRepository, times(1)).findById(feedbackId);
    verify(feedbackRepository, times(1)).save(any(Feedback.class));
  }

  @Test
  void addCommentToFeedback_ShouldAddCommentAndSaveFeedback() {
    Long feedbackId = 1L;
    String comment = "New Comment";

    Feedback mockFeedback =
        new Feedback("Test Title", "Test Content", FeedbackCategory.COMPLAINT, new CitizenId(123L));

    when(feedbackRepository.findById(feedbackId)).thenReturn(Optional.of(mockFeedback));
    when(feedbackRepository.save(any(Feedback.class))).thenReturn(mockFeedback);

    Feedback result = feedbackService.addCommentToFeedback(feedbackId, comment);

    assertNotNull(result);
    assertTrue(result.getComment().contains(comment));

    verify(feedbackRepository, times(1)).findById(feedbackId);
    verify(feedbackRepository, times(1)).save(any(Feedback.class));
  }

  @Test
  void findAllFeedbacksForCitizen_ShouldReturnFeedbackList() {
    Long citizenId = 123L;

    Feedback mockFeedback =
        new Feedback(
            "Test Title", "Test Content", FeedbackCategory.COMPLAINT, new CitizenId(citizenId));

    UserDto mockUserDto = new UserDto(citizenId, "John Doe", "john.doe@example.com", "CITIZEN");

    when(feedbackRepository.findAllByCitizenId(citizenId)).thenReturn(List.of(mockFeedback));
    when(userServiceClient.fetchUserById(citizenId)).thenReturn(mockUserDto);

    List<FeedbackDto> result = feedbackService.findAllFeedbacksForCitizen(citizenId);

    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals(mockFeedback.getId(), result.get(0).id());

    verify(feedbackRepository, times(1)).findAllByCitizenId(citizenId);
    verify(userServiceClient, times(1)).fetchUserById(citizenId);
  }

  @Test
  void updateFeedback_ShouldPublishFeedbackUpdatedEvent() {
    Long feedbackId = 1L;
    String comment = "Updated Comment";
    Long userId = 456L;
    String userRole = "EMPLOYEE";
    String updateType = "comment";

    Feedback mockFeedback =
        new Feedback("Test Title", "Test Content", FeedbackCategory.COMPLAINT, new CitizenId(123L));
    mockFeedback.assignToEmployee(new EmployeeId(userId));
    mockFeedback.setId(feedbackId);
    UserDto mockCitizenDto = new UserDto(123L, "John Doe", "john.doe@example.com", "CITIZEN");
    UserDto mockEmployeeDto =
        new UserDto(userId, "Jane Smith", "jane.smith@example.com", "EMPLOYEE");

    when(feedbackRepository.findById(feedbackId)).thenReturn(Optional.of(mockFeedback));
    when(feedbackRepository.save(any(Feedback.class))).thenReturn(mockFeedback);
    when(userServiceClient.fetchUserById(123L)).thenReturn(mockCitizenDto);
    when(userServiceClient.fetchUserById(userId)).thenReturn(mockEmployeeDto);

    FeedbackDto result =
        feedbackService.updateFeedback(feedbackId, comment, userId, userRole, updateType);

    assertNotNull(result);
    assertEquals(feedbackId, result.id());
    assertTrue(mockFeedback.getComment().contains(comment));

    ArgumentCaptor<FeedbackUpdatedEvent> eventCaptor =
        ArgumentCaptor.forClass(FeedbackUpdatedEvent.class);
    verify(eventPublisher, times(1)).publishEvent(eventCaptor.capture());

    FeedbackUpdatedEvent publishedEvent = eventCaptor.getValue();
    assertNotNull(publishedEvent);
    assertEquals(feedbackId, publishedEvent.getFeedbackId());

    verify(feedbackRepository, times(1)).findById(feedbackId);
    verify(feedbackRepository, times(1)).save(any(Feedback.class));
  }

  @Test
  void findAllOpenFeedbacks_ShouldReturnOpenFeedbacks() {
    Feedback mockFeedback =
        new Feedback("Test Title", "Test Content", FeedbackCategory.COMPLAINT, new CitizenId(123L));

    UserDto mockUserDto = new UserDto(123L, "John Doe", "john.doe@example.com", "CITIZEN");

    when(feedbackRepository.findAllByStatusNot(FeedbackStatus.CLOSED))
        .thenReturn(List.of(mockFeedback));
    when(userServiceClient.fetchUserById(123L)).thenReturn(mockUserDto);

    List<FeedbackDto> result = feedbackService.findAllOpenFeedbacks();

    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals(mockFeedback.getId(), result.get(0).id());

    verify(feedbackRepository, times(1)).findAllByStatusNot(FeedbackStatus.CLOSED);
    verify(userServiceClient, times(1)).fetchUserById(123L);
  }

  @Test
  void getFeedbackById_ShouldReturnFeedbackDto() {
    Long feedbackId = 1L;

    Feedback mockFeedback =
        new Feedback("Test Title", "Test Content", FeedbackCategory.COMPLAINT, new CitizenId(123L));
    UserDto mockCitizenDto = new UserDto(123L, "John Doe", "john.doe@example.com", "CITIZEN");

    when(feedbackRepository.findById(feedbackId)).thenReturn(Optional.of(mockFeedback));
    when(userServiceClient.fetchUserById(123L)).thenReturn(mockCitizenDto);

    FeedbackDto result = feedbackService.getFeedbackById(feedbackId);

    assertNotNull(result);
    assertEquals(mockFeedback.getId(), result.id());
    assertEquals(mockFeedback.getTitle(), result.title());

    verify(feedbackRepository, times(1)).findById(feedbackId);
    verify(userServiceClient, times(1)).fetchUserById(123L);
  }
}
