import de.cityfeedback.exception.WrongUserInputException;
import de.cityfeedback.validator.UserInputValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class UserLoginTest {


    @Test
    void testInvalidUsernameNull() {
        assertThrows(WrongUserInputException.class, () -> UserInputValidator.isValidUsername(null));
    }

    @Test
    void testInvalidUsernameEmpty() {
        assertThrows(WrongUserInputException.class, () -> UserInputValidator.isValidUsername(""));
    }

    @Test
    void testInvalidUsernameTooShort() {
        assertThrows(WrongUserInputException.class, () -> UserInputValidator.isValidUsername("ab"));
    }

    @Test
    void testInvalidUsernameTooLong() {
        assertThrows(WrongUserInputException.class, () -> UserInputValidator.isValidUsername("a".repeat(31)));
    }

    @Test
    void testInvalidUsernameSpecialChars() {
        assertThrows(WrongUserInputException.class, () -> UserInputValidator.isValidUsername("!@#$%^&*()_+-={}:<>?"));
    }

    @Test
    void testInvalidUsernameWhitespace() {
        assertThrows(WrongUserInputException.class, () -> UserInputValidator.isValidUsername(" username "));
    }

    @Test
    void testValidUsername() {
        String validUsername = "validusername123";
        assertTrue(() -> UserInputValidator.isValidUsername(validUsername));
    }

    //TODO:
    // 0. Überprüfung und Ergänzung der Username Validierung
    // 1. Hinzufügen von Testcases zur Email Validierung
    // 2. Test-Klasse zur Validierung von Feedback, für Titel und Beschreibung, und ...

}
