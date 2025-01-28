package de.cityfeedback.userverwaltung.application.services;

import de.cityfeedback.shared.events.UserRegisteredEvent;
import de.cityfeedback.shared.exception.WrongUserInputException;
import de.cityfeedback.shared.validator.Validation;
import de.cityfeedback.userverwaltung.domain.model.User;
import de.cityfeedback.userverwaltung.domain.valueobject.Role;
import de.cityfeedback.userverwaltung.infrastructure.repositories.UserRepository;
import java.util.NoSuchElementException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;
  public final ApplicationEventPublisher eventPublisher;
  private final BCryptPasswordEncoder passwordEncoder;

  public UserService(
      UserRepository userRepository,
      ApplicationEventPublisher eventPublisher,
      BCryptPasswordEncoder passwordEncoder) {
    this.eventPublisher = eventPublisher;
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public User authenticateUser(String email, String password) {
    validateLoginInput(email, password);
    User user = findUserByEmail(email);

    validatePassword(password, user.getPassword());

    UserRegisteredEvent event =
        new UserRegisteredEvent(
            user.getId(), user.getUserName(), user.getEmail(), user.getRole().name());

    eventPublisher.publishEvent(event);
    return user;
  }

  public User registerUser(String userName, String email, String password, Role role) {
    validateUserInputForRegistration(userName, email, password);

    if (userRepository.findByEmail(email).isPresent()) {
      throw new WrongUserInputException("E-Mail-Adresse wird bereits verwendet.");
    }

    String hashedPassword = passwordEncoder.encode(password);

    User newUser = new User(email, hashedPassword, role, userName);

    UserRegisteredEvent event =
        new UserRegisteredEvent(
            newUser.getId(), newUser.getUserName(), newUser.getEmail(), newUser.getRole().name());

    eventPublisher.publishEvent(event);

    return userRepository.save(newUser);
  }

  private static void validateUserInputForRegistration(
      String userName, String email, String password) {
    Validation.validatePassword(password);
    Validation.validateUsername(userName);
    Validation.validateEmail(email);
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
    if (!passwordEncoder.matches(inputPassword, storedPassword)) {
      throw new WrongUserInputException("Das eingegebene Passwort ist falsch.");
    }
  }

  private void validateLoginInput(String email, String password) {
    Validation.validateEmail(email);
    if (password == null || password.isEmpty()) {
      throw new WrongUserInputException("Bitte Passwort eingeben.");
    }
  }
}
