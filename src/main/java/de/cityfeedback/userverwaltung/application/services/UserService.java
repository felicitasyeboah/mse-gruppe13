package de.cityfeedback.userverwaltung.application.services;

import de.cityfeedback.userverwaltung.domain.model.User;
import de.cityfeedback.userverwaltung.domain.events.UserLoggedInEvent;
import de.cityfeedback.userverwaltung.infrastructure.repositories.UserRepository;
import de.cityfeedback.shared.validator.Validation;

import jakarta.transaction.Transactional;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    public final ApplicationEventPublisher eventPublisher;

    public UserService(UserRepository userRepository, ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
        this.userRepository = userRepository;
    }

    //!!!Event ergänzen
    //public final ApplicationEventPublisher eventPublisher;

    //!!! Event ergänzen
    /*public UserService(UserRepository userRepository, ApplicationEventPublisher eventPublisher) {
        this.userRepository = userRepository;
        this.eventPublisher = eventPublisher;
    }

    // Create the domain event
   UserLoggedInEvent event =
         new UserLoggedInEvent(
            user.getId(),
            Getter ergänzen analog zu Feedback?
            getUserByEmail().Email()
            feedback.getTitle(),
            feedback.getContent(),
            feedback.getCitizenId().citizenId(),
            feedback.getStatus().getStatusName(),
            feedback.getCreatedAt());

    // Publish the event
    eventPublisher.publishEvent(event);

    return user;
  }

    */

    @Transactional
    public boolean loginUser (String email, String password){

            // Validate email
            Validation.validateEmail(email);

            System.out.println(email + " " + password);

            // Datenbankabfrage simulieren
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

}