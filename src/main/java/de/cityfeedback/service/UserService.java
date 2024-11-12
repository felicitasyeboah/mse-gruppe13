package de.cityfeedback.service;

import de.cityfeedback.domain.User;
import de.cityfeedback.repository.UserRepository;
import de.cityfeedback.validator.Validation;

import java.util.NoSuchElementException;

public class UserService {
    UserRepository userRepository;

    public UserService() {
    }

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public User loginUser(String username, String email) {

        // validate username
        Validation.validateUsername(username);
        // validate email
        Validation.validateEmail(email);
        // findUserByEmail
        User user = this.userRepository.findUserByEmail(email);
        if (user == null) {
            throw new NoSuchElementException();
        }
        //System.out.println("user: " + user.getEmail());

        // validate that we found a user and it's not null
        return user;
    }
}
