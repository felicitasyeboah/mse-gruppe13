package de.cityfeedback.userverwaltung.domain.listener;

import de.cityfeedback.shared.events.FeedbackUpdatedEvent;
import de.cityfeedback.userverwaltung.application.services.EmailService;
import de.cityfeedback.userverwaltung.application.services.UserService;
import de.cityfeedback.userverwaltung.domain.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component("userFeedbackEventListener")
public class FeedbackEventListener {
  private final UserService userService;
  private final EmailService emailService;
  Logger logger = LoggerFactory.getLogger(FeedbackEventListener.class);

  public FeedbackEventListener(UserService userService, EmailService emailService) {
    this.userService = userService;
    this.emailService = emailService;
  }

  @EventListener
  public void handleFeedbackUpdatedEvent(FeedbackUpdatedEvent event) {
    User user = userService.findUserById(event.citizenId);
    if (user != null) {
      try {
        emailService.sendFeedbackUpdatedEmail(
            user.getEmail(),
            "Feedback Updated",
            "Your feedback has been updated. "
                + event.getFeedbackId()
                + " - "
                + event.getTitle()
                + " - "
                + event.getStatusName());
      } catch (Exception e) {
        logger.error("Error sending email: " + e.getMessage());
      }
    }
  }
}
