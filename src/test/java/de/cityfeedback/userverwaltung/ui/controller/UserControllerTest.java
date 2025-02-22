package de.cityfeedback.userverwaltung.ui.controller;

import static de.cityfeedback.userverwaltung.domain.valueobject.Role.CITIZEN;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.cityfeedback.shared.exception.GlobalExceptionHandler;
import de.cityfeedback.shared.exception.WrongUserInputException;
import de.cityfeedback.userverwaltung.application.services.UserService;
import de.cityfeedback.userverwaltung.domain.model.User;
import de.cityfeedback.userverwaltung.domain.valueobject.Role;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@Import(GlobalExceptionHandler.class)
class UserControllerTest {

  private MockMvc mockMvc;

  @Mock private UserService userService;

  @InjectMocks private UserController userController;

  @BeforeEach
  void setUp() {
    mockMvc =
        MockMvcBuilders.standaloneSetup(userController)
            .setControllerAdvice(new GlobalExceptionHandler())
            .build();
  }

  @Test
  void login_withValidCredentials_returnsSuccessResponse() throws Exception {
    String email = "email@test.de";
    String password = "Password/123";
    User mockUser = new User();
    mockUser.setId(1L);
    mockUser.setUserName("testuser");
    mockUser.setEmail(email);
    mockUser.setPassword(password);
    mockUser.setRole(CITIZEN);

    when(userService.authenticateUser(email, password)).thenReturn(mockUser);

    mockMvc
        .perform(post("/user/login").param("email", email).param("password", password))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message").value("Erfolgreich eingeloggt."))
        .andExpect(jsonPath("$.data.email").value(email))
        .andExpect(jsonPath("$.data.userName").value("testuser"));

    verify(userService, times(1)).authenticateUser(email, password);
  }

  @Test
  void login_withInvalidEmail_returnsBadRequest() throws Exception {
    String invalidEmail = "invalid-email";
    String password = "ValidPassword123";

    when(userService.authenticateUser(invalidEmail, password))
        .thenThrow(new WrongUserInputException("Ungültige E-Mail-Adresse."));

    mockMvc
        .perform(post("/user/login").param("email", invalidEmail).param("password", password))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("Ungültige E-Mail-Adresse."))
        .andExpect(jsonPath("$.data").doesNotExist());
  }

  @Test
  void login_withEmptyPassword_returnsBadRequest() throws Exception {
    String email = "email@test.de";
    String invalidPassword = "";
    when(userService.authenticateUser(email, invalidPassword))
        .thenThrow(new WrongUserInputException("Bitte Passwort eingeben."));
    mockMvc
        .perform(post("/user/login").param("email", email).param("password", invalidPassword))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("Bitte Passwort eingeben."));
  }

  @Test
  void getUserById_withValidUserId_returnsUserResponse() throws Exception {
    Long userId = 1L;
    User mockUser = new User();
    mockUser.setId(userId);
    mockUser.setUserName("testuser");
    mockUser.setEmail("email@test.de");
    mockUser.setRole(CITIZEN);

    when(userService.findUserById(userId)).thenReturn(mockUser);

    mockMvc
        .perform(get("/user/{userId}", userId))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message").doesNotExist())
        .andExpect(jsonPath("$.data.userName").value("testuser"))
        .andExpect(jsonPath("$.data.email").value("email@test.de"))
        .andExpect(jsonPath("$.data.role").value("CITIZEN"));

    verify(userService, times(1)).findUserById(userId);
  }

  @Test
  void getUserById_withNonExistentUserId_returnsNotFound() throws Exception {
    Long userId = 999L;

    when(userService.findUserById(userId))
        .thenThrow(new NoSuchElementException("Benutzer nicht gefunden"));

    mockMvc
        .perform(get("/user/{userId}", userId))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.message").value("Benutzer nicht gefunden"))
        .andExpect(jsonPath("$.data").doesNotExist());

    verify(userService, times(1)).findUserById(userId);
  }

  @Test
  void register_withValidInput_returnsCreatedResponse() throws Exception {
    String userName = "testuser";
    String email = "testuser@test.com";
    String password = "Password123!";
    Role role = CITIZEN;

    User mockUser = new User();
    mockUser.setId(1L);
    mockUser.setUserName(userName);
    mockUser.setEmail(email);
    mockUser.setPassword(password);
    mockUser.setRole(role);

    when(userService.registerUser(userName, email, password, role)).thenReturn(mockUser);

    mockMvc
        .perform(
            post("/user/register")
                .param("userName", userName)
                .param("email", email)
                .param("password", password))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.message").value("Registrierung erfolgreich."))
        .andExpect(jsonPath("$.data.userName").value(userName))
        .andExpect(jsonPath("$.data.email").value(email))
        .andExpect(jsonPath("$.data.role").value("CITIZEN"));

    verify(userService, times(1)).registerUser(userName, email, password, CITIZEN);
  }

  @Test
  void register_withInvalidEmail_returnsBadRequest() throws Exception {
    String userName = "testuser";
    String email = "invalid-email";
    String password = "Password123!";
    when(userService.registerUser(userName, email, password, CITIZEN))
        .thenThrow(new WrongUserInputException("Ungültige E-Mail-Adresse."));
    mockMvc
        .perform(
            post("/user/register")
                .param("userName", userName)
                .param("email", email)
                .param("password", password))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("Ungültige E-Mail-Adresse."));
  }

  @Test
  void register_withInvalidPassword_returnsBadRequest() throws Exception {
    String userName = "testuser";
    String email = "testuser@test.com";
    String password = "123";
    when(userService.registerUser(userName, email, password, CITIZEN))
        .thenThrow(
            new WrongUserInputException(
                "Das Passwort muss folgende Anforderungen erfüllen: Mindestens 8 Zeichen und maximal 20 Zeichen lang, mindestens ein Großbuchstabe, mindestens ein Kleinbuchstabe, mindestens eine Zahl, mindestens ein Sonderzeichen (z.B. !@#$%^&*())."));
    mockMvc
        .perform(
            post("/user/register")
                .param("userName", userName)
                .param("email", email)
                .param("password", password))
        .andExpect(status().isBadRequest())
        .andExpect(
            jsonPath("$.message")
                .value(
                    "Das Passwort muss folgende Anforderungen erfüllen: Mindestens 8 Zeichen und maximal 20 Zeichen lang, mindestens ein Großbuchstabe, mindestens ein Kleinbuchstabe, mindestens eine Zahl, mindestens ein Sonderzeichen (z.B. !@#$%^&*())."));
  }
}
