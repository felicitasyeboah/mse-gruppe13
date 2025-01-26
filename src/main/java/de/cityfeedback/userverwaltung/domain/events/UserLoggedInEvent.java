package de.cityfeedback.userverwaltung.domain.events;

import de.cityfeedback.shared.events.DomainEvent;
import de.cityfeedback.userverwaltung.domain.valueobject.Role;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class UserLoggedInEvent extends DomainEvent {

  Long userId;
  String email;
  String userName;
  Role role;
  private final Instant registeredAt;

  public UserLoggedInEvent(long id, String userName, String email, Role role) {
    this.registeredAt = Instant.now();
  }
}
