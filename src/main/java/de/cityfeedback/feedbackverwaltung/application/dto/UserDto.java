package de.cityfeedback.feedbackverwaltung.application.dto;

import de.cityfeedback.userverwaltung.domain.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
  private Long userId;
  private String userName;
  private String email;
  private String role;

  public static UserDto fromUser(User user) {
    return new UserDto(user.getId(), user.getUserName(), user.getEmail(), user.getRole().name());
  }
}
