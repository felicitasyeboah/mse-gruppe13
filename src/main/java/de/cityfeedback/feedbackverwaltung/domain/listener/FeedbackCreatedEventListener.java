package de.cityfeedback.feedbackverwaltung.domain.listener;

import de.cityfeedback.feedbackverwaltung.domain.events.FeedbackCreatedEvent;
import java.util.logging.Logger;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class FeedbackCreatedEventListener {
  Logger logger = Logger.getLogger(FeedbackCreatedEventListener.class.getName());

  @EventListener
  public void handleFeedbackCreatedEvent(FeedbackCreatedEvent event) {
    // React to the event, such as updating a read model, logging, or sending notifications
    String msg =
        "A new feedback has been created: " + event.getFeedbackId() + " - " + event.getTitle();
    logger.info(msg);
    System.out.println(msg);
    // Handle the event, for example:
    // - Update projections
    // - Send an email notification
    // - Trigger external system interactions
  }
}
