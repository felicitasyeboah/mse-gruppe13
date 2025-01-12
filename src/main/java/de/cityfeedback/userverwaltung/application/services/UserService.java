package de.cityfeedback.userverwaltung.application.services;

import de.cityfeedback.exception.WrongUserInputException;
import de.cityfeedback.userverwaltung.domain.model.User;
import de.cityfeedback.userverwaltung.domain.events.UserLoggedInEvent;
import de.cityfeedback.userverwaltung.infrastructure.repositories.UserRepository;
import de.cityfeedback.shared.validator.Validation;

import jakarta.transaction.Transactional;
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

    public User authenticateUser(String email, String password) {
        User user = findUserByEmailAndPassword(email, password);
        if ((user != null) & (!isPasswordValid(password, user.getPassword()))) {
            return null;
        }

        System.out.println(user.toString());
        publishLoginEvent(user);
        updateUserLogin(user);
        return user;
    }

   public User findUserByEmailAndPassword(String email, String Password) {
      //Exceptions definieren
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new WrongUserInputException("Ungültige E-Mail oder Passwort."));
    }

    private boolean isPasswordValid(String inputPassword, String storedPassword) {
        return inputPassword.equals(storedPassword);
    }

    private void publishLoginEvent(User user) {
        UserLoggedInEvent event = new UserLoggedInEvent(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getRole(),
                user.getUserName()
        );
        eventPublisher.publishEvent(event);
    }

    private void updateUserLogin(User user) {
        userRepository.save(user);
    }
}