package de.cityfeedback.feedbackverwaltung.domain.listener;

import de.cityfeedback.feedbackverwaltung.domain.events.FeedbackCreatedEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.time.LocalDateTime;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class FeedbackCreatedEventListenerTest {

  @Autowired private ApplicationEventPublisher eventPublisher;

  @MockBean private FeedbackCreatedEventListener eventListener;

  @Configuration
  static class TestConfig {
    @Bean
    public FeedbackCreatedEventListener feedbackCreatedEventListener() {
      return new FeedbackCreatedEventListener();
    }
  }

  @Test
  void testHandleFeedbackCreatedEvent() {
    // Arrange: Create a sample FeedbackCreatedEvent
    FeedbackCreatedEvent event =
        new FeedbackCreatedEvent(
            1L,
            "Infrastructure",
            "Pothole on Main Street",
            "There is a large pothole on Main Street that needs urgent attention.",
            101L,
            "Pending",
            LocalDateTime.now());

    // Act: Publish the event
    eventPublisher.publishEvent(event);

    // Assert: Verify that the event listener's method was called once with the event
    verify(eventListener, times(1)).handleFeedbackCreatedEvent(event);
  }
}
