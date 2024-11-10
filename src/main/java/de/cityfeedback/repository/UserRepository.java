package de.cityfeedback.repository;

import de.cityfeedback.domain.User;

public interface UserRepository {
    User findUserByEmail(String email);

}
