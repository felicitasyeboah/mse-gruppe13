package de.cityfeedback.userverwaltung.application.services;

import de.cityfeedback.exception.WrongUserInputException;
import de.cityfeedback.userverwaltung.domain.events.UserLoggedInEvent;
import de.cityfeedback.userverwaltung.domain.model.User;
import de.cityfeedback.userverwaltung.infrastructure.repositories.UserRepository;
import java.util.NoSuchElementException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class UserService {
  private final UserRepository userRepository;
  public final ApplicationEventPublisher eventPublisher;

  // private final BCryptPasswordEncoder passwordEncoder;

  public UserService(UserRepository userRepository, ApplicationEventPublisher eventPublisher) {
    this.eventPublisher = eventPublisher;
    this.userRepository = userRepository;
    // this.passwordEncoder = new BCryptPasswordEncoder();
  }

  // Neue Methode: Passwort hashen
  /*public String hashPassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
  }*/

  public User authenticateUser(String email, String password) {
    User user = findUserByEmail(email);

    validatePassword(password, user.getPassword());

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

  /*public User findUserByEmail(String email) {
    return userRepository
        .findByEmail(email)
        .orElseThrow(() -> new NoSuchElementException("Ungültige E-Mail oder Passwort."));
  }*/

  /* private void validateHashedPassword(String inputPassword, String storedPassword) {
      if (!passwordEncoder.matches(inputPassword, storedPassword)) {
          throw new WrongUserInputException("Das eingegebene Passwort ist falsch.");
      }
  }*/

  private void validatePassword(String inputPassword, String storedPassword) {
    if (!inputPassword.equals(storedPassword)) {
      throw new WrongUserInputException("Das eingegebene Passwort ist falsch.");
    }
  }

  private void publishLoginEvent(User user) {
    UserLoggedInEvent event =
        new UserLoggedInEvent(
            user.getId(), user.getEmail(), user.getPassword(), user.getRole(), user.getUserName());
    eventPublisher.publishEvent(event);
  }
}

/*private void updateUserLogin(User user) {
    userRepository.save(user);
}*/
    /*private boolean isPasswordValid(String inputPassword, String storedPassword) {
        return inputPassword.equals(storedPassword);
    }*/

    /*@Transactional
        public boolean loginUser (String email, String password){
                // Validate email
                Validation.validateEmail(email);

                Optional<User> optionalUser = userRepository.findByEmail(email);

                // Benutzer nicht gefunden
                if (optionalUser.isEmpty()) {
                    return false;
                }

                // Benutzer extrahieren
                User user = optionalUser.get();

                UserLoggedInEvent event =
                    new UserLoggedInEvent(
                            user.getId(),
                            user.getEmail(),
                             user.getPassword(),
                            user.getRole(), user.getUserName()
                            );

                // Passwort prüfen
                boolean loginSuccessful = password.equals(user.getPassword());
                userRepository.save(user);
                if (loginSuccessful) {
                    eventPublisher.publishEvent(event);
                }
                return loginSuccessful;

            // Validate password
                //!! anpassen wenn Passwort gehasht wird: return passwordEncoder.matches(password.plainText(), user.getPassword());

            }

    */
    /*@Transactional
    public boolean authenticateUser(String email, String password) {
       User user = findUserByEmailAndPassword(email, password);

        if (!isPasswordValid(password, user.getPassword())) {
            return false;
        }

        System.out.println(user.toString());
        publishLoginEvent(user);
        updateUserLogin(user);
        return true;
    }*/
