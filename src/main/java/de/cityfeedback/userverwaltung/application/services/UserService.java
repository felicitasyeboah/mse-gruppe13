package de.cityfeedback.userverwaltung.application.services;

import de.cityfeedback.userverwaltung.domain.model.User;
import de.cityfeedback.userverwaltung.domain.valueobject.Email;
import de.cityfeedback.userverwaltung.domain.valueobject.Password;
import de.cityfeedback.userverwaltung.domain.valueobject.UserID;
import de.cityfeedback.userverwaltung.domain.valueobject.UserName;
import de.cityfeedback.userverwaltung.infrastructure.repositories.UserRepository;
import de.cityfeedback.validator.Validation;

import java.util.NoSuchElementException;

import static de.cityfeedback.userverwaltung.domain.valueobject.Role.CITIZEN;
import static de.cityfeedback.userverwaltung.domain.valueobject.UserStatus.AKTIV;

public class UserService {
    public  final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //userRepository.save(user);

    public boolean loginUser(Email email, Password password) {

        User user = userRepository.findUserByEmail(email.email());

        // validate email
        Validation.validateEmail(email.email());


        //User user = new User(new UserID(1L), email, new Password("abds"), CITIZEN, new UserName("testname"), AKTIV);
        if (user == null) {
            return false; // Benutzer nicht gefunden
        }
        System.out.println("user: " + user.toString());
        return user.getPassword().equals(password);


    }
}
