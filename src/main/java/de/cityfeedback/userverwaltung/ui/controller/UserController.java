package de.cityfeedback.userverwaltung.ui.controller;

import de.cityfeedback.exception.WrongUserInputException;
import de.cityfeedback.feedbackverwaltung.application.dto.ApiResponse;
import de.cityfeedback.shared.validator.Validation;
import de.cityfeedback.userverwaltung.application.dto.UserResponse;
import de.cityfeedback.userverwaltung.application.services.UserService;
import de.cityfeedback.userverwaltung.domain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

    @PostMapping("/login")
    public ApiResponse login(@RequestParam String email, @RequestParam String password) {
        try {
            validateInput(email, password);
            User user = userService.authenticateUser(email, password);

            UserResponse userResponse = UserResponse.fromUser(user);
            return new ApiResponse("Erfolgreich eingeloggt.", userResponse);

        } catch (Exception e) {
            return new ApiResponse("Fehler beim Login: " + e.getMessage(), null);
        }
    }

    private void validateInput(String email, String password) {
        Validation.validateEmail(email);
        Validation.validatePassword(password);
    }
}