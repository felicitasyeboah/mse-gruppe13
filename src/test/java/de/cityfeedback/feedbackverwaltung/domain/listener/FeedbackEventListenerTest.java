package de.cityfeedback.feedbackverwaltung.domain.listener;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import de.cityfeedback.feedbackverwaltung.application.dto.UserDto;
import de.cityfeedback.feedbackverwaltung.application.services.UserCacheService;
import de.cityfeedback.feedbackverwaltung.application.services.UserServiceClient;
import de.cityfeedback.feedbackverwaltung.events.FeedbackCreatedEvent;
import de.cityfeedback.shared.events.FeedbackUpdatedEvent;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FeedbackEventListenerTest {

  @Mock private UserCacheService userCacheService;
  @Mock private UserServiceClient userServiceClient;

  @InjectMocks private FeedbackEventListener feedbackEventListener;

  private FeedbackCreatedEvent feedbackCreatedEvent;
  private FeedbackUpdatedEvent feedbackUpdatedEvent;

  @BeforeEach
  void setUp() {
    feedbackCreatedEvent =
        new FeedbackCreatedEvent(
            1L,
            "Infrastructure",
            "Pothole on Main Street",
            "There is a large pothole on Main Street that needs urgent attention.",
            101L,
            "Pending",
            LocalDateTime.now());

    feedbackUpdatedEvent =
        new FeedbackUpdatedEvent(1L, 101L, 202L, "test titel", LocalDateTime.now(), "IN_PROGRESS");
  }

  @Test
  void handleFeedbackCreatedEvent_ShouldReturnExpectedMessage() {
    UserDto userDto = new UserDto(101L, "John Doe", "john.doe@example.com", "CITIZEN");
    when(userCacheService.getUserFromCache(101L)).thenReturn(null);
    when(userServiceClient.fetchUserById(101L)).thenReturn(userDto);

    String result = feedbackEventListener.handleFeedbackCreatedEvent(feedbackCreatedEvent);

    assertEquals("A new feedback has been created: 1 - Pothole on Main Street", result);
    verify(userCacheService, times(1)).getUserFromCache(101L);
    verify(userServiceClient, times(1)).fetchUserById(101L);
    verify(userCacheService, times(1)).cacheUser(userDto);
  }

  @Test
  void handleFeedbackUpdatedEvent_ShouldReturnExpectedMessage() {
    UserDto citizenDto = new UserDto(101L, "John Doe", "john.doe@example.com", "CITIZEN");
    UserDto employeeDto = new UserDto(202L, "Jane Smith", "jane.smith@example.com", "EMPLOYEE");
    when(userServiceClient.fetchUserById(101L)).thenReturn(citizenDto);
    when(userServiceClient.fetchUserById(202L)).thenReturn(employeeDto);

    String result = feedbackEventListener.handleFeedbackUpdatedEvent(feedbackUpdatedEvent);

    assertEquals(
        "Feedback with id: 1 has been updated at: "
            + feedbackUpdatedEvent.getUpdatedAt()
            + " to status: IN_PROGRESS",
        result);
    verify(userServiceClient, times(1)).fetchUserById(101L);
    verify(userServiceClient, times(1)).fetchUserById(202L);
  }
}
