package de.cityfeedback.userverwaltung.application.listener;

import de.cityfeedback.shared.events.FeedbackUpdatedEvent;
import de.cityfeedback.userverwaltung.application.services.EmailService;
import de.cityfeedback.userverwaltung.application.services.UserService;
import de.cityfeedback.userverwaltung.domain.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component("userFeedbackEventListener")
public class ApplicationFeedbackEventListener {
  private final UserService userService;
  private final EmailService emailService;
  public Logger logger = LoggerFactory.getLogger(ApplicationFeedbackEventListener.class);

  public ApplicationFeedbackEventListener(UserService userService, EmailService emailService) {
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
            "Your feedback \""
                + event.getTitle()
                + "\" has been updated. New status: "
                + event.getStatusName()
                + "Log in to see more details.");
      } catch (Exception e) {
        logger.error("Error sending email: " + e.getMessage());
      }
    }
  }
}
