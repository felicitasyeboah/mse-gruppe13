package de.cityfeedback.userverwaltung.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import de.cityfeedback.userverwaltung.domain.model.User;
import de.cityfeedback.userverwaltung.domain.valueobject.Role;
import org.junit.jupiter.api.Test;

public class UserTest {
  @Test
  void equals_withSameObject_returnsTrue() {
    User user = new User(/*1L,*/ "test@example.com", "password123", Role.CITIZEN, "testUser");
    assertEquals(user, user);
  }

  @Test
  void equals_withEqualObjects_returnsTrue() {
    User user1 = new User(/*1L,*/ "test@example.com", "password123", Role.CITIZEN, "testUser");
    User user2 = new User(/*1L, */ "test@example.com", "password123", Role.CITIZEN, "testUser");
    assertEquals(user1, user2);
  }

  @Test
  void equals_withDifferentObjects_returnsFalse() {
    User user1 = new User(/*1L, */ "test@example.com", "password123", Role.CITIZEN, "testUser");
    User user2 = new User(/*2L, */ "test2@example.com", "password456", Role.EMPLOYEE, "testUser2");
    assertNotEquals(user1, user2);
  }

  @Test
  void hashCode_withEqualObjects_returnsSameHashCode() {
    User user1 = new User(/*1L, */ "test@example.com", "password123", Role.CITIZEN, "testUser");
    User user2 = new User(/*1L, */ "test@example.com", "password123", Role.CITIZEN, "testUser");
    assertEquals(user1.hashCode(), user2.hashCode());
  }

  @Test
  void hashCode_withDifferentObjects_returnsDifferentHashCode() {
    User user1 = new User(/*1L,*/ "test@example.com", "password123", Role.CITIZEN, "testUser");
    User user2 = new User(/*2L,*/ "test2@example.com", "password456", Role.EMPLOYEE, "testUser2");
    assertNotEquals(user1.hashCode(), user2.hashCode());
  }
}
