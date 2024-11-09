package de.cityfeedback.service;

import de.cityfeedback.domain.User;
import de.cityfeedback.repository.UserRepository;
import de.cityfeedback.validator.UserInputValidator;

public class UserService {
    UserRepository userRepository;

    public UserService() {
    }

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public void loginUser(String username, String email) {

        // validate username
        UserInputValidator.isValidUsername(username);
        // validate email

        // findUserByEmail
        User user = this.userRepository.findUserByEmail(email);
        System.out.println("user: " + user.getEmail());
        // validate that we found a user and it's not null
    }
}
