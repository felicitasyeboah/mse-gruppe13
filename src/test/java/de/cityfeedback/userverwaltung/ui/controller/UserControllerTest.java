package de.cityfeedback.userverwaltung.ui.controller;

import static org.junit.jupiter.api.Assertions.assertThrows;

import de.cityfeedback.exception.WrongUserInputException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

class UserControllerTest {

  @InjectMocks private UserController userController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void validateInput_withValidEmailAndPassword_doesNotThrowException() {
    String email = "test@example.com";
    String password = "password123";

    userController.validateInput(email, password);
  }

  @Test
  void validateInput_withInvalidEmail_throwsException() {
    String email = "invalid-email";
    String password = "password123";

    assertThrows(
        WrongUserInputException.class, () -> userController.validateInput(email, password));
  }

  @Test
  void validateInput_withInvalidPassword_throwsException() {
    String email = "test@example.com";
    String password = "";

    assertThrows(
        WrongUserInputException.class, () -> userController.validateInput(email, password));
  }
}
