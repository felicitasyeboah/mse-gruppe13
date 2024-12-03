package de.cityfeedback.feedbackverwaltung.domain.listener;

import de.cityfeedback.feedbackverwaltung.domain.events.FeedbackCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class FeedbackCreatedEventListener {
  
  private static final Logger logger = LoggerFactory.getLogger(FeedbackCreatedEventListener.class);

  @EventListener
  public void handleFeedbackCreatedEvent(FeedbackCreatedEvent event) {
    // Reagiere auf das Ereignis, wie z.B. das Aktualisieren eines Read Models, Logging oder Benachrichtigungen
    String msg =
        "A new feedback has been created: " + event.getFeedbackId() + " - " + event.getTitle();
    logger.info(msg);
    // Handle das Ereignis, z.B.:
    // - Projections aktualisieren
    // - E-Mail-Benachrichtigung senden
    // - Externe Systeminteraktionen auslösen
  }
}