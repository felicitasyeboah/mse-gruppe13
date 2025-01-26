package de.cityfeedback.feedbackverwaltung.domain.listener;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import de.cityfeedback.feedbackverwaltung.application.dto.UserDto;
import de.cityfeedback.feedbackverwaltung.application.services.UserCacheService;
import de.cityfeedback.feedbackverwaltung.events.FeedbackCreatedEvent;
import de.cityfeedback.shared.events.FeedbackUpdatedEvent;
import de.cityfeedback.shared.events.UserRegisteredEvent;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FeedbackEventListenerTest {

  @Mock private UserCacheService userCacheService;

  @InjectMocks private FeedbackEventListener feedbackEventListener;

  private FeedbackCreatedEvent feedbackCreatedEvent;
  private FeedbackUpdatedEvent feedbackUpdatedEvent;
  private UserRegisteredEvent userRegisteredEvent;

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
        new FeedbackUpdatedEvent(1L, 101L, 202L, "test title", LocalDateTime.now(), "IN_PROGRESS");

    userRegisteredEvent =
        new UserRegisteredEvent(
            1L, "John Doe", "john.doe@example.com", "CITIZEN", LocalDateTime.now());
  }

  @Test
  void handleFeedbackCreatedEvent_ShouldReturnExpectedMessage() {
    String result = feedbackEventListener.handleFeedbackCreatedEvent(feedbackCreatedEvent);
    assertEquals("A new feedback has been created: 1 - Pothole on Main Street", result);
  }

  @Test
  void handleFeedbackUpdatedEvent_ShouldReturnExpectedMessage() {
    String result = feedbackEventListener.handleFeedbackUpdatedEvent(feedbackUpdatedEvent);
    assertEquals(
        "Feedback with id: 1 has been updated at: "
            + feedbackUpdatedEvent.getUpdatedAt()
            + " to status: IN_PROGRESS",
        result);
  }

  @Test
  void handleUserRegisteredEvent_ShouldCacheUserAndReturnExpectedMessage() {
    ArgumentCaptor<UserDto> userCaptor = ArgumentCaptor.forClass(UserDto.class);

    String result = feedbackEventListener.handleUserRegisteredEvent(userRegisteredEvent);

    verify(userCacheService, times(1)).cacheUser(userCaptor.capture());
    UserDto capturedUser = userCaptor.getValue();

    assertEquals(1L, capturedUser.getUserId());
    assertEquals("John Doe", capturedUser.getUserName());
    assertEquals("john.doe@example.com", capturedUser.getEmail());
    assertEquals("CITIZEN", capturedUser.getRole());

    assertEquals(
        "Benutzerdaten wurden aktualisiert regi: EventId "
            + userRegisteredEvent.getEventId()
            + " - Event occured on:"
            + userRegisteredEvent.getEventOccurredOn()
            + " - userName: John Doe - userEmail: john.doe@example.com - userRole: CITIZEN - userId:1",
        result);
  }
}
