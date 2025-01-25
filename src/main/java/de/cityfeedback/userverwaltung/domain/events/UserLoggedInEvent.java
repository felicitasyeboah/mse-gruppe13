package de.cityfeedback.userverwaltung.domain.events;

import de.cityfeedback.userverwaltung.domain.valueobject.Role;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class UserLoggedInEvent {

  Long userId;
  String email;
  String userName;
  // String password;
  Role role;
  private final Instant registeredAt;

  public UserLoggedInEvent(long id, String userName, String email, Role role) {
    this.registeredAt = Instant.now();
  }
}
