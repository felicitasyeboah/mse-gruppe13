package de.cityfeedback.userverwaltung.domain.events;

import de.cityfeedback.shared.events.DomainEvent;
import de.cityfeedback.userverwaltung.domain.valueobject.Role;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class UserLoggedInEvent extends DomainEvent {
  private Long userId;
  private String email;
  private String userName;
  private Role role;
  private final Instant registeredAt;

  public UserLoggedInEvent(long userId, String userName, String email, Role role) {
    this.userId = userId;
    this.userName = userName;
    this.email = email;
    this.role = role;
    this.registeredAt = Instant.now();
  }
}
