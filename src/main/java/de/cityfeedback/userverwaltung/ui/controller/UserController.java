package de.cityfeedback.userverwaltung.ui.controller;

import de.cityfeedback.shared.dto.ApiResponse;
import de.cityfeedback.shared.validator.Validation;
import de.cityfeedback.userverwaltung.application.dto.UserResponse;
import de.cityfeedback.userverwaltung.application.services.UserService;
import de.cityfeedback.userverwaltung.domain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/login")
  public ResponseEntity<ApiResponse> login(
      @RequestParam String email, @RequestParam String password) {
    validateInput(email, password);
    User user = userService.authenticateUser(email, password);
    UserResponse userResponse = UserResponse.fromUser(user);
    return ResponseEntity.ok(new ApiResponse("Erfolgreich eingeloggt.", userResponse));
  }

  @GetMapping("/{userId}")
  public ResponseEntity<ApiResponse> getUserById(@PathVariable Long userId) {

    User user = userService.findUserById(userId);

    UserResponse userResponse = UserResponse.fromUser(user);
    return ResponseEntity.ok(new ApiResponse(null, userResponse));
  }

  private void validateInput(String email, String password) {
    Validation.validateEmail(email);
    Validation.validatePassword(password);
  }
}
