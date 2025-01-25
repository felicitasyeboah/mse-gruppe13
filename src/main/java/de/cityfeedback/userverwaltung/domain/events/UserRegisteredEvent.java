package de.cityfeedback.userverwaltung.domain.events;

import de.cityfeedback.userverwaltung.domain.valueobject.Role;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class UserRegisteredEvent {

  Long userId;
  String username;
  String email;
  // String password;
  Role role;
  private final Instant loggedInAt;

  public UserRegisteredEvent(long id, String username, String email, Role role) {
    this.loggedInAt = Instant.now();
  }
}
