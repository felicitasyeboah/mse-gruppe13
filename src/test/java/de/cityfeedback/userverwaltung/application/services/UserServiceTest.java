package de.cityfeedback.userverwaltung.application.services;

import static de.cityfeedback.userverwaltung.domain.valueobject.Role.*;
import static org.junit.jupiter.api.Assertions.*;

import de.cityfeedback.userverwaltung.domain.model.User;
import de.cityfeedback.userverwaltung.domain.valueobject.*;
import de.cityfeedback.userverwaltung.infrastructure.repositories.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;

import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private UserService userService;

    private User user;
    //private FeedbackRequest request;

    @BeforeEach
    void setup() {

        //request = new UserRequest("XX", "XX", 1L, "XX");

        //!!!request und controller einfügen

        // Set up test data
        String email = "email";
        String password = "123";
        String username = "name";
        UserId userId = new UserId(1L);


     /*   user = new User(userID, email, password, CITIZEN, username, AKTIV);
        user.setId(1L);
        user.setRole(CITIZEN);
        user.setUserName(new UserName("citizen1"));
        user.setEmail(new Email("citizen1@test.de"));
        user.setUserStatus(AKTIV);
        user.setPassword(new Password("testpassword"));
*/
        user = new User(userId, email, password, CITIZEN, username);
    }


    @Test
    public void testLogin_Success() {
    //Arrange

    //!!! muss das raus?

    //mit echter DB ergänzen
    //UserRepository userRepository = mock(UserRepository.class);

    //when(userRepository.save(any(User.class))).thenReturn(user);

        //Testdaten aus Datenbank - Email-Adresse und Passwort
        String email = "employee1@test.de";
        String password = "password10";

        //when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        //!!! ist das hier nötig?
        UserService userService = new UserService(userRepository);

        // Act
        boolean result = userService.loginUser("employee1@test.de", "password10");

        System.out.println(user.toString());


        /*    User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        when(userRepository.findUserByEmail(email)).thenReturn(Optional.of(user));
        System.out.println(user.toString());*/

        // Assert
        assertTrue(result, "Login should succeed with correct credentials");

        //wird derzeit noch zu oft aufgerufen, Fehler suchen
        verify(userRepository, times(1)).findUserByEmail(email);

    }


    @Test
    public void testLogin_Failure_InvalidPassword() {
        // Arrange
        UserRepository userRepository = mock(UserRepository.class);
        UserService userService = new UserService(userRepository);

        String password = "wrongPassword";
        UserId userid = new UserId(1L);
        String email = "citizen1@test.de";
        String userName = "testcitizen1";

        User mockUser = new User(userid, email, password, CITIZEN, userName);

        // Act
        boolean result = userService.loginUser(email, password);

        // Assert
        assertFalse(result, "Login should fail with incorrect password");

        //wird derzeit noch zu oft aufgerufen, Fehler suchen
        verify(userRepository, times(1)).findUserByEmail(email);
    }

    @Test
    public void testLogin_Failure_UserNotFound() {
        // Arrange
        UserRepository userRepository = mock(UserRepository.class);
        UserService userService = new UserService(userRepository);

        String email = "nonExistentUser@email.de";
        String password = "irrelevantPassword";

        // Act
        boolean result = userService.loginUser(email, password);

        // Assert
        assertFalse(result, "Login should fail if user is not found");

        //wird derzeit noch zu oft aufgerufen, UserService anpassen?
        verify(userRepository, times(1)).findUserByEmail(email);
    }


}