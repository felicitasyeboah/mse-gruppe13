package de.cityfeedback.feedbackverwaltung.domain.listener;

import de.cityfeedback.feedbackverwaltung.domain.events.FeedbackCreatedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class FeedbackCreatedEventListener {

    @EventListener
    public void handleFeedbackCreatedEvent(FeedbackCreatedEvent event) {
        // Die Geschäftslogik für die Verarbeitung des Feedback-Ereignisses
        System.out.println("A new feedback has been created: " + event.getFeedbackId() + " - " + event.getTitle());

        // Weitere Aktionen, z. B.:
        // - Update von Read-Modellen
        // - Senden von Benachrichtigungen
        // - Interaktion mit externen Systemen
    }
}
