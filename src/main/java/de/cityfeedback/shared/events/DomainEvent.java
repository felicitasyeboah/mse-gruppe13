package de.cityfeedback.shared.events;

import java.time.Instant;
import java.util.UUID;
import lombok.Getter;

@Getter
public abstract class DomainEvent {
  private final String eventId;
  private final Instant eventOccurredOn;

  protected DomainEvent() {
    this.eventId = UUID.randomUUID().toString();
    this.eventOccurredOn = Instant.now();
  }
}
