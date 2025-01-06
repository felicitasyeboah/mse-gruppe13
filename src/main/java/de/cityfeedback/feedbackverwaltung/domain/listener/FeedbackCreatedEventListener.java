package de.cityfeedback.feedbackverwaltung.domain.listener;

import de.cityfeedback.feedbackverwaltung.domain.events.FeedbackCreatedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class FeedbackCreatedEventListener {

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
}
