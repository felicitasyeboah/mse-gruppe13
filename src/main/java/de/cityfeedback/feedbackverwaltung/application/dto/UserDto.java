package de.cityfeedback.feedbackverwaltung.application.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class UserDto {
  private Long userId;
  private String userName;
  private String email;
  private String role;
}
