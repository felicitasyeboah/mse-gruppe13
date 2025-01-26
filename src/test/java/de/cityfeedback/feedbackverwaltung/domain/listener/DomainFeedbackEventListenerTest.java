package de.cityfeedback.feedbackverwaltung.domain.listener;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.cityfeedback.feedbackverwaltung.application.services.UserCacheService;
import de.cityfeedback.feedbackverwaltung.domain.events.FeedbackCreatedEvent;
import de.cityfeedback.shared.events.FeedbackUpdatedEvent;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DomainFeedbackEventListenerTest {

  @Mock private UserCacheService userCacheService;

  @InjectMocks private DomainFeedbackEventListener domainFeedbackEventListener;

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
        new FeedbackUpdatedEvent(1L, 101L, 202L, "test title", LocalDateTime.now(), "IN_PROGRESS");
  }

  @Test
  void handleFeedbackCreatedEvent_ShouldReturnExpectedMessage() {
    String result = domainFeedbackEventListener.handleFeedbackCreatedEvent(feedbackCreatedEvent);
    assertEquals("A new feedback has been created: 1 - Pothole on Main Street", result);
  }

  @Test
  void handleFeedbackUpdatedEvent_ShouldReturnExpectedMessage() {
    String result = domainFeedbackEventListener.handleFeedbackUpdatedEvent(feedbackUpdatedEvent);
    assertEquals(
        "Feedback with id: 1 has been updated at: "
            + feedbackUpdatedEvent.getUpdatedAt()
            + " to status: IN_PROGRESS",
        result);
  }
}
