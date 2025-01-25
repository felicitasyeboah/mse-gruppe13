package de.cityfeedback.userverwaltung.application.dto;

import de.cityfeedback.userverwaltung.domain.valueobject.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserResponseDto {
  private Long id;
  private String userName;
  private String email;
  private Role role;
}
