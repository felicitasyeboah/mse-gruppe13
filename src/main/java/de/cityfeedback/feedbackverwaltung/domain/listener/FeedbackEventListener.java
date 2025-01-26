package de.cityfeedback.feedbackverwaltung.domain.listener;

import de.cityfeedback.feedbackverwaltung.application.dto.UserDto;
import de.cityfeedback.feedbackverwaltung.application.services.UserCacheService;
import de.cityfeedback.feedbackverwaltung.events.FeedbackCreatedEvent;
import de.cityfeedback.shared.events.FeedbackUpdatedEvent;
import de.cityfeedback.shared.events.UserRegisteredEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component("feedbackFeedbackEventListener")
public class FeedbackEventListener {
  private final UserCacheService userCacheService;

  public FeedbackEventListener(UserCacheService userCacheService) {
    this.userCacheService = userCacheService;
  }

  @EventListener
  public String handleFeedbackCreatedEvent(FeedbackCreatedEvent event) {
    // Reagiere auf das Ereignis, wie z.B. das Aktualisieren eines Read Models, Logging oder
    // Benachrichtigungen
    return "A new feedback has been created: " + event.getFeedbackId() + " - " + event.getTitle();
    // Handle das Ereignis, z.B.:
    // - Projections aktualisieren
    // - E-Mail-Benachrichtigung senden
    // - Externe Systeminteraktionen auslösen
  }

  @EventListener
  public String handleFeedbackUpdatedEvent(FeedbackUpdatedEvent event) {
    // Reagiere auf das Ereignis, wie z.B. das Aktualisieren eines Read Models, Logging oder
    // Benachrichtigungen
    return "Feedback with id: "
        + event.getFeedbackId()
        + " has been updated at: "
        + event.getUpdatedAt()
        + " to status: "
        + event.getStatusName();

    // Handle das Ereignis, z.B.:
    // - Projections aktualisieren
    // - E-Mail-Benachrichtigung senden
    // - Externe Systeminteraktionen auslösen
  }

  @EventListener
  public String handleUserRegisteredEvent(UserRegisteredEvent event) {
    UserDto user =
        new UserDto(event.getUserId(), event.getUsername(), event.getEmail(), event.getRole());
    userCacheService.cacheUser(user);
    return "Benutzerdaten wurden aktualisiert regi: EventId "
        + event.getEventId()
        + " - Event occured on:"
        + event.getEventOccurredOn()
        + " - userName: "
        + user.getUserName()
        + " - userEmail: "
        + user.getEmail()
        + " - userRole: "
        + user.getRole()
        + " - userId:"
        + user.getUserId();
  }
}
