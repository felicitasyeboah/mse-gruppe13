package de.cityfeedback.userverwaltung.application.services;

import static de.cityfeedback.userverwaltung.domain.valueobject.UserStatus.*;
import static de.cityfeedback.userverwaltung.domain.valueobject.Role.*;
import static org.junit.jupiter.api.Assertions.*;

import de.cityfeedback.userverwaltung.domain.model.User;
import de.cityfeedback.userverwaltung.domain.valueobject.*;
import de.cityfeedback.userverwaltung.infrastructure.repositories.UserRepository;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Test
    public void testLogin_Success() {
    //Arrange
    UserRepository userRepository = mock(UserRepository.class);
    UserService userService = new UserService(userRepository);

    UserID userid = new UserID(1L);
    Email email = new Email("testemail@domain.de");
    Password password = new Password("testpasswort");
    UserName userName = new UserName("logintest");
    User mockUser = new User(userid, email, password, CITIZEN, userName, AKTIV);

    when(userRepository.findUserByEmail(email.email())).thenReturn(mockUser);

    // Act
    boolean result = userService.loginUser(email, password);
    // Assert
     assertTrue(result, "Login should succeed with correct credentials");
     verify(userRepository, times(1)).findUserByEmail(email.email());
    }


    @Test
    public void testLogin_Failure_InvalidPassword() {
        // Arrange
        UserRepository userRepository = mock(UserRepository.class);
        UserService userService = new UserService(userRepository);

        UserName username = new UserName ("testname");
        Password password = new Password("wrongPassword");
        UserID userid = new UserID(1L);
        Email email = new Email("username@domain.de");
        UserName userName = new UserName("testname");

        User mockUser = new User(userid, email, new Password("passwort"), CITIZEN, userName, AKTIV);
        when(userRepository.findUserByEmail(email.email())).thenReturn(mockUser);

        // Act
        boolean result = userService.loginUser(email, password);

        // Assert
        assertFalse(result, "Login should fail with incorrect password");
        verify(userRepository, times(1)).findUserByEmail(email.email());
    }

    @Test
    public void testLogin_Failure_UserNotFound() {
        // Arrange
        UserRepository userRepository = mock(UserRepository.class);
        UserService userService = new UserService(userRepository);

        Email email = new Email("nonExistentUser@email.de");
        Password password = new Password("irrelevantPassword");

        when(userRepository.findUserByEmail(email.email())).thenReturn(null);

        // Act
        boolean result = userService.loginUser(email, password);

        // Assert
        assertFalse(result, "Login should fail if user is not found");
        verify(userRepository, times(1)).findUserByEmail(email.email());
    }


}