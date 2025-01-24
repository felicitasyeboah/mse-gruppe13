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
  String username;
  String password;
  Role role;
  private final Instant loggedInAt;

  public UserLoggedInEvent(long id, String email, String Password, Role role, String Name) {
    this.loggedInAt = Instant.now();
  }
}
