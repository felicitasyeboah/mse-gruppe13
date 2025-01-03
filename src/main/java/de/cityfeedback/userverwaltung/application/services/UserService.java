package de.cityfeedback.userverwaltung.application.services;

import de.cityfeedback.userverwaltung.domain.model.User;
import de.cityfeedback.userverwaltung.infrastructure.repositories.UserRepository;
import de.cityfeedback.validator.Validation;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    //!!!Event ergänzen
    //public final ApplicationEventPublisher eventPublisher;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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

    //userRepository.save(user);

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

            // Passwort prüfen
            return password.equals(user.getPassword());



            // Fetch user from repository

            //funktioniert noch nicht!!!

            //Datenabfrage
            //Optional<User> optionalUser = userRepository.findUserByEmail(email);
            //Optional<User> optionalUser = userRepository.findUserByEmailAndPassword(email, password);


            // User user = userRepository.
            //Testausgabe, da optional User immer null - DB-Abfrage funktioniert nicht?

            //temporäres Passwort als Spalte in Tabelle vorhanden

            // Validate password
            //!! anpassen wenn Passwort gehasht wird: return passwordEncoder.matches(password.plainText(), user.getPassword());

        }

}