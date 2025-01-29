package de.cityfeedback.userverwaltung.application.listener;

import static org.mockito.Mockito.*;

import de.cityfeedback.shared.events.FeedbackUpdatedEvent;
import de.cityfeedback.userverwaltung.application.services.EmailService;
import de.cityfeedback.userverwaltung.application.services.UserService;
import de.cityfeedback.userverwaltung.domain.model.User;
import de.cityfeedback.userverwaltung.domain.valueobject.Role;
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
class ApplicationFeedbackEventListenerTest {

  @Mock private UserService userService;

  @Mock private EmailService emailService;

  @Mock private Logger logger;

  @InjectMocks private ApplicationFeedbackEventListener applicationFeedbackEventListener;

  @BeforeEach
  void setUp() {
    applicationFeedbackEventListener.logger = logger;
  }

  @Test
  void handleFeedbackUpdatedEventWithValidUserSendsEmail() throws MessagingException {
    FeedbackUpdatedEvent event =
        new FeedbackUpdatedEvent(
            1L, 1L, 1L, "Feedback Title", LocalDateTime.now(), "Updated Status");
    User user = new User("john.doe@example.com", "password123", Role.CITIZEN, "John Doe");

    when(userService.findUserById(1L)).thenReturn(user);

    applicationFeedbackEventListener.handleFeedbackUpdatedEvent(event);

    verify(emailService, times(1))
        .sendFeedbackUpdatedEmail(
            eq("john.doe@example.com"),
            eq("Feedback Updated"),
            eq(
                "Your feedback \"Feedback Title\" has been updated. New status: Updated StatusLog in to see more details."));
  }

  @Test
  void handleFeedbackUpdatedEventWithNullUserDoesNotSendEmail() throws MessagingException {
    FeedbackUpdatedEvent event =
        new FeedbackUpdatedEvent(
            1L, 1L, 1L, "Feedback Title", LocalDateTime.now(), "Updated Status");

    when(userService.findUserById(1L)).thenReturn(null);

    applicationFeedbackEventListener.handleFeedbackUpdatedEvent(event);

    verify(emailService, times(0)).sendFeedbackUpdatedEmail(anyString(), anyString(), anyString());
  }

  @Test
  void handleFeedbackUpdatedEventEmailServiceThrowsExceptionLogsError() throws MessagingException {
    FeedbackUpdatedEvent event =
        new FeedbackUpdatedEvent(
            1L, 1L, 1L, "Feedback Title", LocalDateTime.now(), "Updated Status");
    User user = new User("john.doe@example.com", "password123", Role.CITIZEN, "John Doe");

    when(userService.findUserById(1L)).thenReturn(user);
    doThrow(new RuntimeException("Email service error"))
        .when(emailService)
        .sendFeedbackUpdatedEmail(anyString(), anyString(), anyString());

    applicationFeedbackEventListener.handleFeedbackUpdatedEvent(event);

    verify(logger, times(1)).error(eq("Error sending email: Email service error"));
  }
}
