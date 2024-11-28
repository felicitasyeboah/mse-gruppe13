package de.cityfeedback.feedbackverwaltung.events;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@AllArgsConstructor
@Getter
public class FeedbackCreatedEvent {
    public final long feedbackId;
    public final long userId;
    public final Instant createdAt;
}
