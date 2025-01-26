package de.cityfeedback.shared.events;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class UserRegisteredEvent extends DomainEvent {

  Long userId;
  String username;
  String email;
  String role;
  private final LocalDateTime registeredAt;

  public UserRegisteredEvent(long id, String username, String email, String role) {
    this.userId = id;
    this.username = username;
    this.email = email;
    this.role = role;
    this.registeredAt = LocalDateTime.now();
  }
}
