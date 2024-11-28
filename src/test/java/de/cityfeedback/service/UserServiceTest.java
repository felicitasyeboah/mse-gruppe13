package de.cityfeedback.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import de.cityfeedback.domain.User;
import de.cityfeedback.repository.InMemoryUserRepository;
import de.cityfeedback.validator.Validation;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTest {

  @InjectMocks public InMemoryUserRepository userRepository;

  @InjectMocks public UserService userService;

  @Test
  public void testUserInDatabase_shouldFindUserInDatabase() {
    InMemoryUserRepository userRepository = new InMemoryUserRepository();
    UserService userService = new UserService(userRepository);

    String email = "username@domain.de";
    String username = "abds";
    String role = "citizen";

    Validation.validateEmail(email);

    User user = userService.loginUser(username, email);

    // User user = new User();
    // user.setId(1L);
    // user.setEmail(email);
    // user.setUsername(username);
    // user.setRole(role);
    assertNotNull(user);
    assertEquals(email, user.getEmail());
    assertNotNull(user.getId());
    assertNotNull(user.getRole());
    System.out.println(user.getEmail() + ", " + user.getUsername() + ", " + user.getRole());
  }
}
