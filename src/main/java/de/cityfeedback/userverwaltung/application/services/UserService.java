package de.cityfeedback.userverwaltung.application.services;

import de.cityfeedback.exception.WrongUserInputException;
import de.cityfeedback.userverwaltung.domain.events.UserLoggedInEvent;
import de.cityfeedback.userverwaltung.domain.model.User;
import de.cityfeedback.userverwaltung.infrastructure.repositories.UserRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class UserService {

  private final UserRepository userRepository;
  public final ApplicationEventPublisher eventPublisher;

  public UserService(UserRepository userRepository, ApplicationEventPublisher eventPublisher) {
    this.eventPublisher = eventPublisher;
    this.userRepository = userRepository;
  }

  public User authenticateUser(String email, String password) {
    System.out.println("Authenticating user with email: " + email + ", password: " + password);

    // Find the user by email (no userId needed for login)
    User user = findUserByEmail(email);

    // Validate the password
    validatePassword(password, user.getPassword());

    // Publish the login event
    publishLoginEvent(user);

    return user;
  }

  public User findUserByEmail(String email) {
    return userRepository
        .findByEmail(email)
        .orElseThrow(() -> new NoSuchElementException("Kein Nutzer mit dieser E-Mail-Adresse."));
  }

  public User findUserById(long userId) {
    return userRepository
        .findById(userId)
        .orElseThrow(() -> new NoSuchElementException("Benutzer nicht gefunden."));
  }

  private void validatePassword(String inputPassword, String storedPassword) {
    if (!inputPassword.equals(storedPassword)) {
      throw new WrongUserInputException("Das eingegebene Passwort ist falsch.");
    }
  }

  private void publishLoginEvent(User user) {
    UserLoggedInEvent event = new UserLoggedInEvent(
        user.getId(), user.getEmail(), user.getPassword(), user.getRole(), user.getUserName());
    eventPublisher.publishEvent(event);
  }
}
