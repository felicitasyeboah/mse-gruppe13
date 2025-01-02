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
        // Set up test data
        //request = new UserRequest("XX", "XX", 1L, "XX");

        //!!!request und controller einfügen
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
        //!!!feedback.setCitizenId(new CitizenId(request.citizenId()));


        user = new User(userId, email, password, CITIZEN, username);
        //user.setId(10L);
        //user.setRole(EMPLOYEE);
        //user.setUserName("startemployee");
        //user.setEmail("start@test.de");
        //user.setPassword("testpassword");
    }



    @Test
    public void testLogin_Success() {
    //Arrange

    //!!! muss das raus?

    //mit echter DB ergänzen
    //UserRepository userRepository = mock(UserRepository.class);

    //when(userRepository.save(any(User.class))).thenReturn(user);
        String email = "employee1@test.de";
        String password = "password10";

        System.out.println(user.getId());
        //when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        System.out.println(user.toString());
        boolean result = userService.loginUser("employee1@test.de", "password10");
        System.out.println(user.toString());
        //UserService userService = new UserService(userRepository);

    //Email email = new Email("citizen1@test.de");


    /*    User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        when(userRepository.findUserByEmail(email)).thenReturn(Optional.of(user));
        System.out.println(user.toString());*/
    // Act
   // boolean result = userService.loginUser(email, password);

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

        //when(userRepository.findByEmail(email).orElseThrow(Optional.of(user));
        //when(userRepository.findByEmail(email).thenReturn(Optional.of(user)));

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

        //when(userRepository.findByEmail(email.email())).thenReturn(null);
        //System.out.println(email);

        // Act
        boolean result = userService.loginUser(email, password);

        // Assert
        assertFalse(result, "Login should fail if user is not found");

        //wird derzeit noch zu oft aufgerufen, UserService anpassen?
        verify(userRepository, times(1)).findUserByEmail(email);
    }


}