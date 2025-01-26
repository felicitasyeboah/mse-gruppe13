package de.cityfeedback.userverwaltung.domain.events;

import static org.junit.jupiter.api.Assertions.*;

import de.cityfeedback.userverwaltung.domain.valueobject.Role;
import java.time.Instant;
import org.junit.jupiter.api.Test;

class UserLoggedInEventTest {

  @Test
  void testConstructorAndGetters() {
    // Arrange
    long userId = 1L;
    String userName = "TestUser";
    String email = "test@example.com";
    Role role = Role.CITIZEN;
    Instant before = Instant.now();

    // Act
    UserLoggedInEvent event = new UserLoggedInEvent(userId, userName, email, role);
    Instant after = Instant.now();

    // Assert
    assertEquals(userId, event.getUserId());
    assertEquals(userName, event.getUserName());
    assertEquals(email, event.getEmail());
    assertEquals(role, event.getRole());
    assertNotNull(event.getRegisteredAt());
    assertTrue(event.getRegisteredAt().isAfter(before) || event.getRegisteredAt().equals(before));
    assertTrue(event.getRegisteredAt().isBefore(after) || event.getRegisteredAt().equals(after));
  }

  @Test
  void testSetters() {
    // Arrange
    UserLoggedInEvent event =
        new UserLoggedInEvent(1L, "InitialUser", "initial@example.com", Role.CITIZEN);

    // Act
    event.setUserId(2L);
    event.setUserName("UpdatedUser");
    event.setEmail("updated@example.com");
    event.setRole(Role.EMPLOYEE);

    // Assert
    assertEquals(2L, event.getUserId());
    assertEquals("UpdatedUser", event.getUserName());
    assertEquals("updated@example.com", event.getEmail());
    assertEquals(Role.EMPLOYEE, event.getRole());
  }

  @Test
  void testRegisteredAtIsFinal() {
    // Arrange
    UserLoggedInEvent event = new UserLoggedInEvent(1L, "User", "user@example.com", Role.CITIZEN);
    Instant originalRegisteredAt = event.getRegisteredAt();

    // Act
    // Versuche, den Wert von registeredAt zu ändern (indirekt, da es final ist, ist das nicht
    // möglich)

    // Assert
    assertNotNull(originalRegisteredAt);
    assertSame(
        originalRegisteredAt,
        event.getRegisteredAt(),
        "The registeredAt timestamp should remain constant and unchanged.");
  }
}
