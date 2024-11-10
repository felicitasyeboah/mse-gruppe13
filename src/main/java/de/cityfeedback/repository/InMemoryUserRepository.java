package de.cityfeedback.repository;

import de.cityfeedback.domain.User;

import java.util.HashMap;
import java.util.Map;

public class InMemoryUserRepository implements UserRepository {
    private final Map<String, User> userDatabase = new HashMap<>();
    private static Long idCounter = 1L;

    public InMemoryUserRepository() {
        this.initUserDb();
    }

    private void initUserDb() {
        User user = new User(idCounter++, "abds", "username@domain.de", "citizen");
        userDatabase.put(user.getEmail(), user);
    }

    @Override
    public User findUserByEmail(String email) {
        return userDatabase.get(email);
    }

}
