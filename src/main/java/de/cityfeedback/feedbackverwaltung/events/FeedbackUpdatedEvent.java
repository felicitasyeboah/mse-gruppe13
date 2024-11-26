package de.cityfeedback.feedbackverwaltung.events;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@AllArgsConstructor
@Getter
public class FeedbackUpdatedEvent {
    public final long feednackId;
    public final Instant updatedAt;
}
