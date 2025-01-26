package de.cityfeedback.feedbackverwaltung.domain.listener;

import de.cityfeedback.feedbackverwaltung.domain.events.FeedbackCreatedEvent;
import de.cityfeedback.shared.events.FeedbackUpdatedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component("feedbackEventListener")
public class DomainFeedbackEventListener {

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
}
