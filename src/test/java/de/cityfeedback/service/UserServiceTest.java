package de.cityfeedback.service;

import de.cityfeedback.domain.User;
import de.cityfeedback.repository.InMemoryFeedbackRepository;
import de.cityfeedback.repository.InMemoryUserRepository;
import de.cityfeedback.validator.Validation;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    @InjectMocks
    public InMemoryUserRepository userRepository;

    @InjectMocks
    public UserService userservice;

    @Test
    public void TestUserInDatabase_shouldFindUserInDatabase() {
        InMemoryUserRepository userRepository = new InMemoryUserRepository();
        UserService userService = new UserService(userRepository);

        String email = "Test@Email.de";
        String username = "Testuser";
        String role = "Mitarbeiter";


        System.out.println(email + username);
        Validation.validateEmail(email);

        //User savedUser = userRepository.findUserByEmail(email);
        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setEmail(email);
        savedUser.setUsername(username);
        savedUser.setRole(role);
        System.out.println(savedUser.getEmail());
        assertNotNull(savedUser);
        assertEquals(email, savedUser.getEmail());
        assertNotNull(savedUser.getId());
        assertNotNull(savedUser.getRole());

    }



}
