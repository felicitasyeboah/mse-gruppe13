package de.cityfeedback.userverwaltung.ui.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.cityfeedback.feedbackverwaltung.application.dto.ApiResponse;
import de.cityfeedback.shared.GlobalExceptionHandler;
import de.cityfeedback.userverwaltung.application.dto.UserResponse;
import de.cityfeedback.userverwaltung.application.services.UserService;
import de.cityfeedback.userverwaltung.domain.model.User;
import de.cityfeedback.userverwaltung.domain.valueobject.Role;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@Import(GlobalExceptionHandler.class)
class UserControllerTest {

  @Autowired private MockMvc mockMvc;

  @Mock private UserService userService;

  @InjectMocks private UserController userController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    mockMvc =
        MockMvcBuilders.standaloneSetup(userController)
            .setControllerAdvice(new GlobalExceptionHandler())
            .build();
  }

  @Test
  void login_withValidEmailAndPassword_returnsSuccessResponse() throws Exception {
    String email = "email@test.de";
    String password = "password123";
    User mockUser = new User();
    mockUser.setId(1L);
    mockUser.setUserName("testuser");
    mockUser.setEmail(email);
    mockUser.setPassword(password);
    mockUser.setRole(Role.CITIZEN);

    // when(userService.authenticateUser(email, password)).thenReturn(mockUser);

    when(userService.authenticateUser(eq(email), eq(password))).thenReturn(mockUser);
    System.out.println(mockUser.toString());

    // Act & Assert
    mockMvc
        .perform(post("/user/login").param("email", email).param("password", password))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message").value("Erfolgreich eingeloggt."))
        .andExpect(jsonPath("$.data.email").value(email))
        .andExpect(jsonPath("$.data.userName").value("testuser"));
    /*.andDo(print()) // Druckt die Antwort auf die Konsole
    .andExpect(status().isOk());*/

    // Verifizieren, dass der Mock aufgerufen wurde
    verify(userService, times(1)).authenticateUser(email, password);
  }

  @Test
  void login_withInvalidEmail_returnsErrorResponse() throws Exception {
    String invalidEmail = "invalid-email"; // No domain or '@'
    String password = "ValidPassword123";

    mockMvc
        .perform(post("/user/login").param("email", invalidEmail).param("password", password))
        .andExpect(status().isBadRequest()) // Expect a 400 response
        .andExpect(jsonPath("$.message").value("Ungültige E-Mail-Adresse."));
  }

  /* @Test
     void login_withInvalidEmail_returnsErrorResponse() {
       // Arrange
       String invalidEmail = "invalid-email";
       String password = "ValidPassword123";

       // Act
       ResponseEntity<ApiResponse> response = userController.login(invalidEmail, password);

       // Assert
       assertNotNull(response, "The response should not be null");
       assertEquals(
           "Fehler beim Login: Ungültige E-Mail-Adresse.",
           response.getBody(),
           "The error message is incorrect");
       assertNull(response.getBody(), "The data in the response should be null");
     }
  */

  @Test
  void login_withInvalidPassword_returnsErrorResponse() {
    // Arrange
    String email = "email@test.de";
    String invalidPassword = "";

    // Act
    ResponseEntity<ApiResponse> response = userController.login(email, invalidPassword);

    // Assert
    assertNotNull(response, "The response should not be null");
    assertEquals(
        "Fehler beim Login: Bitte Passwort eingeben.",
        response.getBody(),
        "The error message is incorrect");
    assertNull(response.getBody(), "The response data should be null");
  }

  @Test
  void testGetUserById_UserExists() {
    // Arrange
    Long userId = 1L;
    User mockUser = new User();
    mockUser.setUserName("testname");
    mockUser.setRole(Role.valueOf("CITIZEN"));
    mockUser.setEmail("email@test.de");

    when(userService.findUserById(userId)).thenReturn(mockUser);

    // Act
    ResponseEntity<ApiResponse> response = userController.getUserById(userId);

    // Assert
    // Überprüfen, ob der HTTP-Status korrekt ist
    assertEquals(HttpStatus.OK, response.getStatusCode(), "Expected HTTP status 200 (OK)");

    // Überprüfen, ob die API-Antwort nicht null ist
    ApiResponse apiResponse = response.getBody();
    assertNotNull(apiResponse, "The response body should not be null");
    assertEquals(
        "Benutzer gefunden.", apiResponse.getMessage(), "The success message is incorrect");

    // Überprüfen, ob die Nutzerdaten korrekt sind
    UserResponse userResponse = (UserResponse) apiResponse.getData();
    assertNotNull(userResponse, "The user data should not be null");
    // assertEquals(""); fromUser und "XXX"
    /*assertEquals("testname", userResponse.getUserName(), "The username is incorrect");
    assertEquals(Role.CITIZEN, userResponse.getRole(), "The role is incorrect");
    assertEquals("email@test.de", userResponse.getEmail(), "The email is incorrect");*/

    // Verifizieren, dass der Service genau einmal aufgerufen wurde
    verify(userService, times(1)).findUserById(userId);
  }

  @Test
  void testGetUserById_UserNotFound() {
    Long userId = 1L;

    when(userService.findUserById(userId))
        .thenThrow(new NoSuchElementException("Benutzer nicht gefunden"));

    ResponseEntity<ApiResponse> response = userController.getUserById(userId);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    ApiResponse apiResponse = response.getBody();
    assertNotNull(apiResponse);
    assertEquals("Benutzer nicht gefunden", apiResponse.getMessage());
    assertNull(apiResponse.getData());

    verify(userService, times(1)).findUserById(userId);
  }
}
