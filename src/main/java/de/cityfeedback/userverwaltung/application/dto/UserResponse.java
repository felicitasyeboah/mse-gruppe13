package de.cityfeedback.userverwaltung.application.dto;

import de.cityfeedback.userverwaltung.domain.model.User;
import de.cityfeedback.userverwaltung.domain.valueobject.Role;

public record UserResponse(Long userId, String userName, String email, Role role) {

  public static UserResponse fromUser(User user) {
    return new UserResponse(user.getId(), user.getUserName(), user.getEmail(), user.getRole());
  }
}
