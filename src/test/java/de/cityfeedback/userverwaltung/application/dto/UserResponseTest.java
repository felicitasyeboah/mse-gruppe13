package de.cityfeedback.userverwaltung.application.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import de.cityfeedback.userverwaltung.domain.model.User;
import de.cityfeedback.userverwaltung.domain.valueobject.Role;
import org.junit.jupiter.api.Test;

class UserResponseTest {

  @Test
  void fromUser_createsUserResponseSuccessfully() {
    User user = new User(1L, "test@example.com", "password123", Role.EMPLOYEE, "testUser");
    UserResponse userResponse = UserResponse.fromUser(user);

    assertEquals(1L, userResponse.userId());
    assertEquals("testUser", userResponse.userName());
    assertEquals("test@example.com", userResponse.email());
    assertEquals("password123", userResponse.password());
    assertEquals(Role.EMPLOYEE, userResponse.role());
  }

  @Test
  void fromUser_withNullFields_createsUserResponseWithNullFields() {
    User user = new User();
    UserResponse userResponse = UserResponse.fromUser(user);

    assertEquals(0, userResponse.userId());
    assertNull(userResponse.userName());
    assertNull(userResponse.email());
    assertNull(userResponse.password());
    assertNull(userResponse.role());
  }
}
