package de.cityfeedback.userverwaltung.ui.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import de.cityfeedback.feedbackverwaltung.application.dto.ApiResponse;
import de.cityfeedback.userverwaltung.application.dto.UserResponse;
import de.cityfeedback.userverwaltung.application.services.UserService;
import de.cityfeedback.userverwaltung.domain.model.User;
import de.cityfeedback.userverwaltung.domain.valueobject.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class UserControllerTest {

  @Mock private UserService userService;

  @InjectMocks private UserController userController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void login_withValidEmailAndPassword_returnsSuccessResponse() {
    // Arrange
    String email = "test@example.com";
    String password = "password123";
    User mockUser = new User();
    UserResponse expectedUserResponse = UserResponse.fromUser(mockUser);

    // Mocking the service call
    when(userService.authenticateUser(email, password)).thenReturn(mockUser);

    // Act
    ApiResponse response = userController.login(email, password);

    // Assert
    assertNotNull(response, "The response should not be null");
    assertEquals(
        "Erfolgreich eingeloggt.", response.getMessage(), "The success message is incorrect");
    assertEquals(expectedUserResponse, response.getData(), "The returned user data is incorrect");
    verify(userService, times(1)).authenticateUser(email, password);
  }

  @Test
  void login_withInvalidEmail_returnsErrorResponse() {
    // Arrange
    String invalidEmail = "invalid-email";
    String password = "ValidPassword123";

    // Act
    ApiResponse response = userController.login(invalidEmail, password);

    // Assert
    assertNotNull(response, "The response should not be null");
    assertEquals(
        "Fehler beim Login: Ungültige E-Mail-Adresse.",
        response.getMessage(),
        "The error message is incorrect");
    assertNull(response.getData(), "The data in the response should be null");
  }

  @Test
  void login_withInvalidPassword_returnsErrorResponse() {
    // Arrange
    String email = "test@example.com";
    String invalidPassword = ""; // Beispiel für ein ungültiges Passwort

    // Act
    ApiResponse response = userController.login(email, invalidPassword);

    // Assert
    assertNotNull(response, "The response should not be null");
    assertEquals(
        "Fehler beim Login: Bitte Passwort eingeben.",
        response.getMessage(),
        "The error message is incorrect");
    assertNull(response.getData(), "The response data should be null");
  }

  @Test
  void testGetUserById_UserExists() {
    // Arrange
    Long userId = 1L;
    User mockUser = new User();
    mockUser.setUserName("JohnDoe");
    mockUser.setRole(Role.valueOf("CITIZEN"));
    mockUser.setEmail("johndoe@example.com");

    when(userService.findUserById(userId)).thenReturn(mockUser);

    // Act
    ApiResponse response = userController.getUserById(userId);

    // Assert
    // assertEquals(200, response.getStatusCodeValue());
    // ApiResponse apiResponse = response.getBody();

    // assertNotNull(response.getMessage());
    assertEquals("Benutzer gefunden.", response.getMessage(), "The success message is incorrect");

    UserResponse userResponse = (UserResponse) response.getData();
    assertNotNull(userResponse);

    /*assertEquals("JohnDoe", userResponse.getUserName());
    assertEquals("Admin", userResponse.getRole());
    assertEquals("johndoe@example.com", userResponse.getEmail());*/

    verify(userService, times(1)).findUserById(userId);
  }

  @Test
  void testGetUserById_UserNotFound() {
    // Arrange
    Long userId = 1L;

    when(userService.findUserById(userId)).thenReturn(null);

    // Act
    ApiResponse response = userController.getUserById(userId);

    // Assert
    // assertEquals(404, response.getStatusCodeValue());
    // ApiResponse apiResponse = response.getBody();
    assertNotNull(response);
    assertEquals("Benutzer nicht gefunden", response.getMessage());
    assertNull(response.getData());

    verify(userService, times(1)).findUserById(userId);
  }
}
