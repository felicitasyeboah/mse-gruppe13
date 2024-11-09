import de.cityfeedback.exception.WrongUserInputException;
import de.cityfeedback.repository.InMemoryUserRepository;
import de.cityfeedback.validator.UserInputValidator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class UserLoginTest {
    //private UserService service;
    private InMemoryUserRepository repository;

    @BeforeAll
    public static void init() {
        System.out.println("BeforeAll init() method called");
    }

    @BeforeEach
    public void setUp() {
        repository = new InMemoryUserRepository();
        //service = new UserService(repository);
    }

//    @Test
//    void testLoginByEmail() {
//        assertDoesNotThrow(() -> service.loginUser("abds", "username@domain.de"));
//        assertThrows(WrongUserInputException.class, () -> service.loginUser("abds/sadas", "username@domain.de"));
//    }

    @Test
    void testUserNotFound() {
        assertNull(repository.findUserByEmail("test@domain.de"));
    }

    @Test
    void testFoundUser() {
        assertNotNull(repository.findUserByEmail("username@domain.de"));
    }

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
