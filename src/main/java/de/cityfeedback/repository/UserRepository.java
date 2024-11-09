package de.cityfeedback.repository;

import de.cityfeedback.domain.User;
import org.springframework.data.repository.Repository;

public interface UserRepository extends Repository<User, Long> {
    User findUserByEmail(String email);

}
