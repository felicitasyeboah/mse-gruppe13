package de.cityfeedback.userverwaltung.ui.controller;

import static org.junit.jupiter.api.Assertions.assertThrows;

import de.cityfeedback.exception.WrongUserInputException;
import de.cityfeedback.feedbackverwaltung.application.dto.ApiResponse;
import de.cityfeedback.userverwaltung.application.dto.UserResponse;
import de.cityfeedback.userverwaltung.application.services.UserService;
import de.cityfeedback.userverwaltung.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;
  @InjectMocks private UserController userController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void validateInput_withValidEmailAndPassword_doesNotThrowException() {
    //String email = "test@example.com";
    //String password = "password123";

   // @Test
   // void login_validCredentials_returnsSuccessResponse() {
        // Arrange
        String email = "test@example.com";
        String password = "ValidPassword123";
        User mockUser = new User();
        when(userService.authenticateUser(email, password)).thenReturn(mockUser);

        UserResponse expectedUserResponse = UserResponse.fromUser(mockUser);

    userController.validateInput(email, password);
  }
        // Act
  /*      ApiResponse response = userController.login(email, password);

        // Assert
        assertNotNull(response);
        assertEquals("Erfolgreich eingeloggt.", response.getMessage());
        assertEquals(expectedUserResponse, response.getData());
        verify(userService, times(1)).authenticateUser(email, password);
    }
*/
  @Test
  void validateInput_withInvalidEmail_throwsException() {
    String email = "invalid-email";
    String password = "password123";
    //@Test
    //void login_invalidEmail_throwsValidationException() {
        // Arrange
        String invalidEmail = "invalid-email";
        String password = "ValidPassword123";

    assertThrows(
        WrongUserInputException.class, () -> userController.validateInput(email, password));
  }

  @Test
  void validateInput_withInvalidPassword_throwsException() {
    String email = "test@example.com";
    String password = "";
    //@Test
    //void login_serviceThrowsException_returnsErrorResponse() {
        // Arrange
        String email = "test@example.com";
        String password = "ValidPassword123";
        when(userService.authenticateUser(email, password)).thenThrow(new RuntimeException("Authentication failed"));

    assertThrows(
        WrongUserInputException.class, () -> userController.validateInput(email, password));
  }
        // Act
        ApiResponse response = userController.login(email, password);

        // Assert
        assertNotNull(response);
        assertTrue(response.getMessage().contains("Fehler beim Login"));
        assertNull(response.getData());
    }
}
