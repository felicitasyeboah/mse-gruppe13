package de.cityfeedback.validator;

import de.cityfeedback.exception.WrongUserInputException;

public class UserInputValidator {
    public static boolean isValidUsername(String username) {
        if (username == null || username.isEmpty()) {
            throw new WrongUserInputException("Username cannot be null or empty");
        }

        if (username.length() < 3 || username.length() > 30) {
            throw new WrongUserInputException("Username must be between 3 and 30 characters");
        }

        if (!username.matches("^[a-zA-Z0-9]+$")) {
            throw new WrongUserInputException("Username can only contain letters and numbers");
        }

        return true;
    }
}