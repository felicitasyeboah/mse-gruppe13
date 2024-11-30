package de.cityfeedback.userverwaltung.infrastructure.repositories;

import de.cityfeedback.userverwaltung.domain.model.User;
import de.cityfeedback.userverwaltung.domain.valueobject.*;

import static de.cityfeedback.userverwaltung.domain.valueobject.Role.CITIZEN;
import static de.cityfeedback.userverwaltung.domain.valueobject.UserStatus.AKTIV;

import de.cityfeedback.userverwaltung.domain.model.User;

import java.util.Optional;



public interface UserRepository {
    User findUserByEmail(String email);

}


