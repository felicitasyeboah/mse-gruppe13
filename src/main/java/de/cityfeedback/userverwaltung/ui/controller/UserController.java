package de.cityfeedback.userverwaltung.ui.controller;

import de.cityfeedback.feedbackverwaltung.application.dto.ApiResponse;
import de.cityfeedback.feedbackverwaltung.application.dto.FeedbackResponse;
import de.cityfeedback.shared.validator.Validation;
import de.cityfeedback.userverwaltung.application.dto.UserResponse;
import de.cityfeedback.userverwaltung.application.services.UserService;
import de.cityfeedback.userverwaltung.domain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public String login(@RequestParam String email, @RequestParam String password) {
        //Exception-Handling
        //leere Eingaben abfangen?

        boolean loginSuccessful = userService.authenticateUser(email, password);
        System.out.println("Controller Login: " + email + " " + password);

        User user = userService.findUser(email); // Benutzer aus der Datenbank abrufen
        UserResponse userResponse = UserResponse.fromUser(user);
        ApiResponse response = new ApiResponse("Erfolgreich eingeloggt mit E-Mail: " +  user.getEmail(), userResponse);
        return loginSuccessful ? "Login erfolgreich, User: " + user.toString() : "Login fehlgeschlagen";
    }

}