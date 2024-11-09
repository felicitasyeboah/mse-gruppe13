package de.cityfeedback;

import de.cityfeedback.exception.WrongUserInputException;
import de.cityfeedback.validator.UserInputValidator;

public class Main {

    public static void main(String[] args) {
        try {
            String username = "asdb@asda";
            UserInputValidator.isValidUsername(username);
        } catch (Exception e) {
            if (e instanceof WrongUserInputException) {
                System.out.println(e.getMessage());
            }
        }
    }
}