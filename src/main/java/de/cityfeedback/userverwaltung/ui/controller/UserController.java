package de.cityfeedback.userverwaltung.ui.controller;

import de.cityfeedback.feedbackverwaltung.application.dto.ApiResponse;
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
        //Exception-Handling
        //leere Eingaben abfangen?

        if (isInputInvalid(email, password)) {
            return createErrorResponse("E-Mail oder Passwort darf nicht leer sein.");
        }

        try {
            boolean loginSuccessful = userService.authenticateUser(email, password);

            if (!loginSuccessful) {
                return createErrorResponse("Login fehlgeschlagen. Ungültige E-Mail oder Passwort.");
            }

            System.out.println("Controller Login: " + email + " " + password);

            User user = userService.findUser(email); // Benutzer aus der Datenbank abrufen
            UserResponse userResponse = UserResponse.fromUser(user);

            return createSuccessResponse("Erfolgreich eingeloggt.", userResponse);
        } catch (NoSuchElementException e) {
            return createErrorResponse("Benutzer nicht gefunden: " + e.getMessage());
        } catch (Exception e) {
            return createErrorResponse("Ein unerwarteter Fehler ist aufgetreten: " + e.getMessage());
        }
    }

    private boolean isInputInvalid(String email, String password) {
        return email == null || email.isEmpty() || password == null || password.isEmpty();
    }

    private ApiResponse createErrorResponse(String message) {
        return new ApiResponse(message, null);
    }

    private ApiResponse createSuccessResponse(String message, UserResponse userResponse) {
        return new ApiResponse(message, userResponse);
    }

}