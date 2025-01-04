package de.cityfeedback.userverwaltung.application.services;

import static de.cityfeedback.userverwaltung.domain.valueobject.Role.*;
import static org.junit.jupiter.api.Assertions.*;

import de.cityfeedback.exception.WrongUserInputException;
import de.cityfeedback.userverwaltung.domain.model.User;
import de.cityfeedback.userverwaltung.domain.valueobject.*;
import de.cityfeedback.userverwaltung.infrastructure.repositories.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private UserService userService;

    private User mockUser;
    //private FeedbackRequest request;

    @BeforeEach
    void setup() {

        //request = new UserRequest("XX", "XX", 1L, "XX");

        //!!!request und controller einfügen

        // Set up test data
        Long userId = 10L;
        String email = "employee1@test.de";
        String password = "password10";
        String username = "testemployee1";
        Role role = CITIZEN;

        mockUser = new User(userId, email, password, CITIZEN, username);
    }


    @Test
    public void testLogin_Success() {
        // Arrange
        String email = "employee1@test.de";
        String password = "password10";

        // Verhalten von findByEmail mocken
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(mockUser));

        // Act
        boolean result = userService.loginUser(email, password);

        // Assert
        assertTrue(result, "Login should succeed with correct credentials");
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    public void testLogin_Failure_InvalidPassword() {
        // Arrange
        String email = "employee1@test.de";
        String wrongPassword = "wrongPassword";

        // Verhalten von findByEmail mocken
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(mockUser));

        // Act
        boolean result = userService.loginUser(email, wrongPassword);

        // Assert
        assertFalse(result, "Login should fail with incorrect password");
        verify(userRepository, times(1)).findByEmail(email);
    }


    @Test
    public void testLogin_Failure_UserNotFound() {
        // Arrange
        String email = "nonexistent@test.de";
        String password = "irrelevantPassword";

        // Verhalten von findByEmail mocken
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Act
        boolean result = userService.loginUser(email, password);

        // Assert
        assertFalse(result, "Login should fail if user is not found");
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    public void testLogin_Failure_InvalidEmailFormat() {
        // Arrange
        String invalidEmail = "invalid-email-format";
        String password = "password";

        // Act & Assert
        assertThrows(WrongUserInputException.class, () -> {
            userService.loginUser(invalidEmail, password);
        });
    }

    @Test
    public void testLogin_PerformanceWithManyUsers() {
        // Arrange
        String email = "employee1@test.de";
        String password = "password";
        List<User> mockUsers = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            mockUsers.add(new User((long) i, "user" + i + "@test.de", "password", CITIZEN, "name"));
        }
        mockUsers.add(new User(1001L, email, password, CITIZEN, "name"));

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(mockUsers.get(1000)));

        // Act
        boolean result = userService.loginUser(email, password);

        // Assert
        assertTrue(result, "Login should succeed even with many users in database");
    }

    /*@Test
    public void testLogin_Success_HashedPassword() {
        // Arrange
        String email = "user@test.de";
        String plainPassword = "password";
        String hashedPassword = "$2a$10$eW5ZiEtDWbz...";
        User user = new User(1L, email, hashedPassword, "CITIZEN");

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(plainPassword, hashedPassword)).thenReturn(true);

        // Act
        boolean result = userService.loginUser(email, plainPassword);

        // Assert
        assertTrue(result, "Login should succeed with hashed password");
    }*/

}