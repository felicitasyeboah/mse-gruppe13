package de.cityfeedback.userverwaltung.ui.controller;

import de.cityfeedback.feedbackverwaltung.application.dto.ApiResponse;
import de.cityfeedback.shared.validator.Validation;
import de.cityfeedback.userverwaltung.application.dto.UserResponse;
import de.cityfeedback.userverwaltung.application.services.UserService;
import de.cityfeedback.userverwaltung.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void login_validCredentials_returnsSuccessResponse() {
        // Arrange
        String email = "test@example.com";
        String password = "ValidPassword123";
        User mockUser = new User();
        when(userService.authenticateUser(email, password)).thenReturn(mockUser);

        UserResponse expectedUserResponse = UserResponse.fromUser(mockUser);

        // Act
        ApiResponse response = userController.login(email, password);

        // Assert
        assertNotNull(response);
        assertEquals("Erfolgreich eingeloggt.", response.getMessage());
        assertEquals(expectedUserResponse, response.getData());
        verify(userService, times(1)).authenticateUser(email, password);
    }


    @Test
    void login_invalidEmail_throwsValidationException() {
        // Arrange
        String invalidEmail = "invalid-email";
        String password = "ValidPassword123";

        //when(userService.authenticateUser(invalidEmail, password))
        //        .thenThrow(new NoSuchElementException("Ungültige E-Mail oder Passwort."));

        // Act & Assert
        ApiResponse response = userController.login(invalidEmail, password);

        // Assert
        assertNotNull(response);
    //    System.out.println(response.getMessage());
        assertTrue(response.getMessage().contains("Ungültige E-Mail"));
        assertNull(response.getData());
        verify(userService, never()).authenticateUser(invalidEmail, password);
    }

    @Test
    void login_serviceThrowsException_returnsErrorResponse() {
        // Arrange
        String email = "test@example.com";
        String password = "ValidPassword123";
        when(userService.authenticateUser(email, password)).thenThrow(new RuntimeException("Authentication failed"));

        // Act
        ApiResponse response = userController.login(email, password);

        // Assert
        assertNotNull(response);
        assertTrue(response.getMessage().contains("Fehler beim Login"));
        assertNull(response.getData());
    }
}