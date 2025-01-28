package de.cityfeedback.feedbackverwaltung.domain.listener;

import de.cityfeedback.feedbackverwaltung.domain.events.FeedbackCreatedEvent;
import de.cityfeedback.shared.events.FeedbackUpdatedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component("feedbackEventListener")
public class DomainFeedbackEventListener {

  @EventListener
  public String handleFeedbackCreatedEvent(FeedbackCreatedEvent event) {
    return "A new feedback has been created: " + event.getFeedbackId() + " - " + event.getTitle();
  }

  @EventListener
  public String handleFeedbackUpdatedEvent(FeedbackUpdatedEvent event) {
    return "Feedback with id: "
        + event.getFeedbackId()
        + " has been updated at: "
        + event.getUpdatedAt()
        + " to status: "
        + event.getStatusName();
  }
}
