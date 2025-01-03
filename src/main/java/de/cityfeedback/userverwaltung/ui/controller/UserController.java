package de.cityfeedback.userverwaltung.ui.controller;

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
    public String login(@RequestParam String email, @RequestParam String password, User user) {
        boolean loginSuccessful = userService.loginUser(email, password, user);
        System.out.println("Controller Login: " + email + " " + password);
        return loginSuccessful ? "Login erfolgreich" + user.toString() : "Login fehlgeschlagen";
    }

}