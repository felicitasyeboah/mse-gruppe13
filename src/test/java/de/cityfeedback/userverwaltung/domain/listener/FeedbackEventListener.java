package de.cityfeedback.userverwaltung.domain.listener;

import static org.mockito.Mockito.*;

import de.cityfeedback.shared.events.FeedbackUpdatedEvent;
import de.cityfeedback.userverwaltung.application.services.EmailService;
import de.cityfeedback.userverwaltung.application.services.UserService;
import de.cityfeedback.userverwaltung.domain.model.User;
import jakarta.mail.MessagingException;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

@ExtendWith(MockitoExtension.class)
class FeedbackEventListenerTest {

  @Mock private UserService userService;
  @Mock private EmailService emailService;
  @Mock private Logger logger;

  @InjectMocks private FeedbackEventListener feedbackEventListener;

  private FeedbackUpdatedEvent feedbackUpdatedEvent;
  private User user;

  @BeforeEach
  void setUp() {
    feedbackUpdatedEvent =
        new FeedbackUpdatedEvent(
            1L, 101L, 202L, "Pothole on Main Street", LocalDateTime.now(), "IN_PROGRESS");

    user = new User();
    user.setId(101L);
    user.setEmail("john.doe@example.com");

    // Inject the mocked logger into the FeedbackEventListener
    feedbackEventListener.logger = logger;
  }

  @Test
  void handleFeedbackUpdatedEvent_ShouldSendEmail() throws MessagingException {
    when(userService.findUserById(101L)).thenReturn(user);

    feedbackEventListener.handleFeedbackUpdatedEvent(feedbackUpdatedEvent);

    verify(emailService, times(1))
        .sendFeedbackUpdatedEmail(
            eq("john.doe@example.com"),
            eq("Feedback Updated"),
            eq("Your feedback has been updated. 1 - Pothole on Main Street - IN_PROGRESS"));
  }

  @Test
  void handleFeedbackUpdatedEvent_ShouldLogErrorWhenEmailFails() throws MessagingException {
    when(userService.findUserById(101L)).thenReturn(user);
    doThrow(new RuntimeException("Email service unavailable"))
        .when(emailService)
        .sendFeedbackUpdatedEmail(anyString(), anyString(), anyString());

    feedbackEventListener.handleFeedbackUpdatedEvent(feedbackUpdatedEvent);

    verify(logger, times(1)).error(contains("Error sending email: Email service unavailable"));
  }

  @Test
  void handleFeedbackUpdatedEvent_ShouldNotSendEmailWhenUserNotFound() throws MessagingException {
    when(userService.findUserById(101L)).thenReturn(null);

    feedbackEventListener.handleFeedbackUpdatedEvent(feedbackUpdatedEvent);

    verify(emailService, never()).sendFeedbackUpdatedEmail(anyString(), anyString(), anyString());
  }
}
