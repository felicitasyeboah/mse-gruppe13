package de.cityfeedback.shared.events;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class UserRegisteredEventTest {

    @Test
    void testConstructorAndGetters() {
        Long userId = 1L;
        String username = "testuser";
        String email = "testuser@test.com";
        String role = "CITIZEN";

        UserRegisteredEvent event = new UserRegisteredEvent(userId, username, email, role);

        assertEquals(userId, event.getUserId());
        assertEquals(username, event.getUsername());
        assertEquals(email, event.getEmail());
        assertEquals(role, event.getRole());
        assertNotNull(event.getRegisteredAt());
    }

    @Test
    void testSetters() {
        UserRegisteredEvent event = new UserRegisteredEvent(1L, "testuser", "testuser@test.com", "CITIZEN");

        Long newUserId = 2L;
        String newUsername = "newuser";
        String newEmail = "newuser@test.com";
        String newRole = "ADMIN";

        event.setUserId(newUserId);
        event.setUsername(newUsername);
        event.setEmail(newEmail);
        event.setRole(newRole);

        assertEquals(newUserId, event.getUserId());
        assertEquals(newUsername, event.getUsername());
        assertEquals(newEmail, event.getEmail());
        assertEquals(newRole, event.getRole());
    }
}