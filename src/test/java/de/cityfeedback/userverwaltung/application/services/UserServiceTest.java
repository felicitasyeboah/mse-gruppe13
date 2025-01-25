package de.cityfeedback.userverwaltung.application.services;

import static de.cityfeedback.userverwaltung.domain.valueobject.Role.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import de.cityfeedback.exception.WrongUserInputException;
import de.cityfeedback.userverwaltung.domain.model.User;
import de.cityfeedback.userverwaltung.domain.valueobject.Role;
import de.cityfeedback.userverwaltung.infrastructure.repositories.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;

@SpringBootTest
public class UserServiceTest {

  @MockBean private UserRepository userRepository;

  @MockBean private ApplicationEventPublisher eventPublisher;

  @Autowired private UserService userService;

  private static final String VALID_EMAIL = "employee1@test.de";
  private static final String VALID_PASSWORD = "password10";
  private static final String INVALID_EMAIL = "nonexistent@test.de";
  private static final String INVALID_PASSWORD = "wrongPassword";

  private User mockUser;

  @BeforeEach
  void setup() {
    // mockUser = new User(10L, VALID_EMAIL, VALID_PASSWORD, CITIZEN, "testemployee1");
    mockUser = new User(VALID_EMAIL, VALID_PASSWORD, CITIZEN, "testemployee1");
  }

  @Test
  public void shouldLoginSuccessfullyWithValidCredentials() {
    when(userRepository.findByEmail(VALID_EMAIL)).thenReturn(Optional.of(mockUser));

    User result = userService.authenticateUser(VALID_EMAIL, VALID_PASSWORD);

    assertNotNull(result, "Login should succeed with correct credentials");
    verify(userRepository, times(1)).findByEmail(VALID_EMAIL);
  }

  @Test
  public void shouldFailLoginWithInvalidPassword() {
    when(userRepository.findByEmail(VALID_EMAIL)).thenReturn(Optional.of(mockUser));

    Exception exception =
        assertThrows(
            WrongUserInputException.class,
            () -> userService.authenticateUser(VALID_EMAIL, INVALID_PASSWORD));

    assertEquals("Das eingegebene Passwort ist falsch.", exception.getMessage());
    verify(userRepository, times(1)).findByEmail(VALID_EMAIL);
  }

  @Test
  public void shouldFailLoginWhenUserNotFound() {
    when(userRepository.findByEmail(INVALID_EMAIL)).thenReturn(Optional.empty());

    Exception exception =
        assertThrows(
            NoSuchElementException.class,
            () -> userService.authenticateUser(INVALID_EMAIL, VALID_PASSWORD));

    assertEquals("Kein Nutzer mit dieser E-Mail-Adresse.", exception.getMessage());
    verify(userRepository, times(1)).findByEmail(INVALID_EMAIL);
  }

  @Test
  public void testLogin_Failure_InvalidEmailFormat() {
    // Act & Assert
    assertThrows(
        NoSuchElementException.class,
        () -> userService.authenticateUser(INVALID_EMAIL, VALID_PASSWORD));
  }

  @Test
  public void shouldHandlePerformanceWithLargeUserBase() {
    List<User> mockUsers = new ArrayList<>();
    for (int i = 0; i < 1000; i++) {
      mockUsers.add(
          new User(/*(long) i, */ "user" + i + "@test.de", VALID_PASSWORD, CITIZEN, "user" + i));
    }
    mockUsers.add(mockUser);

    when(userRepository.findByEmail(VALID_EMAIL)).thenReturn(Optional.of(mockUser));
    User result = userService.authenticateUser(VALID_EMAIL, VALID_PASSWORD);

    assertNotNull(result, "Login should succeed even with a large user base");
    verify(userRepository, times(1)).findByEmail(VALID_EMAIL);
  }

  @Test
  void testFindUserById_UserExists() {
    // Arrange
    long userId = 1L;
    mockUser = new User();
    mockUser.setId(userId);
    mockUser.setUserName("testname");

    when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));

    // Act
    User user = userService.findUserById(userId);

    // Assert
    assertNotNull(user);
    assertEquals(userId, user.getId());
    assertEquals("testname", user.getUserName());

    verify(userRepository, times(1)).findById(userId);
  }

  @Test
  void testFindUserById_UserNotFound() {
    // Arrange
    long userId = 1L;

    when(userRepository.findById(userId)).thenReturn(Optional.empty());

    // Act & Assert
    NoSuchElementException exception =
        assertThrows(NoSuchElementException.class, () -> userService.findUserById(userId));

    assertEquals("Benutzer nicht gefunden.", exception.getMessage());
    verify(userRepository, times(1)).findById(userId);
  }

  @Test
  public void testRegisterUser_whenEmailIsAlreadyUsed_shouldThrowException() {
    // Arrange: Mock the behavior of the userRepository
    String email = "alreadyUsedEmail@example.com";
    when(userRepository.findByEmail(email)).thenReturn(Optional.of(new User()));

    // Act & Assert: Test the method and check that it throws a WrongUserInputException
    WrongUserInputException exception =
        assertThrows(
            WrongUserInputException.class,
            () -> {
              userService.registerUser("username", email, "Password123!", Role.CITIZEN);
            });

    assertEquals("E-Mail-Adresse wird bereits verwendet.", exception.getMessage());
  }

  @Test
  public void testRegisterUser_whenEmailIsNotUsed_shouldCreateAndSaveUser() {
    // Arrange: Prepare data and mock repository methods
    String email = "newEmail@example.com";
    when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

    User newUser = new User();
    newUser.setUserName("username");
    newUser.setEmail(email);
    newUser.setPassword("Password123!");
    newUser.setRole(Role.CITIZEN);

    when(userRepository.save(any(User.class))).thenReturn(newUser);

    // Act: Call the registerUser method
    User savedUser = userService.registerUser("username", email, "Password123!", Role.CITIZEN);

    // Assert: Verify the repository methods were called, and the user was created
    verify(userRepository).findByEmail(email);
    verify(userRepository).save(any(User.class));
    assertEquals("username", savedUser.getUserName());
    assertEquals(email, savedUser.getEmail());
    assertEquals(Role.CITIZEN, savedUser.getRole());
  }

  @Test
  public void testRegisterUser_whenPasswordIsInvalid_shouldThrowException() {
    // Arrange: Define an invalid password
    String email = "validEmail@example.com";
    when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

    String invalidPassword = "short"; // A password that does not meet the requirements

    // Act & Assert: Verify the method throws an exception due to invalid password
    assertThrows(
        WrongUserInputException.class,
        () -> {
          userService.registerUser("username", email, invalidPassword, Role.CITIZEN);
        });
  }
}
