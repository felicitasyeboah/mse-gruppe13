package de.cityfeedback.userverwaltung.infrastructure.repositories;

import de.cityfeedback.userverwaltung.domain.model.User;
import de.cityfeedback.userverwaltung.domain.valueobject.*;
import org.aspectj.internal.lang.reflect.StringToType;



import java.util.HashMap;
import java.util.Map;

import static de.cityfeedback.userverwaltung.domain.valueobject.Role.CITIZEN;
import static de.cityfeedback.userverwaltung.domain.valueobject.UserStatus.*;



public class InMemoryUserRepository implements UserRepository {
    private final Map<String, User> userDatabase = new HashMap<>();
    private static long idCounter = 1L;

    public InMemoryUserRepository() {
        this.initUserDb();
    }

    private void initUserDb() {
        Email email = new Email("start@domain.de");
        Password password = new Password("startpasswort");
        UserName username = new UserName("starttestuser");
        UserID id = new UserID(idCounter++);
        User user = new User(id, email, password, CITIZEN, username, AKTIV);

        userDatabase.put(email.email(), user);
    }



    @Override
    public User findUserByEmail(String email) {
        System.out.println(userDatabase.get(email));
        return userDatabase.get(email);
    }

}
/*public class InMemoryUserRepository implements UserRepository {
    private final Map<String, User> userDatabase = new HashMap<>();
    private static Long idCounter = 1L;

    public InMemoryUserRepository() {
        this.initUserDb();
    }

    private void initUserDb() {
        UserID id=new UserID(String.valueOf(idCounter++));
        UserStatus status = AKTIV;
        Role role = CITIZEN;
        User user = new User(id, "username@domain.de", "passwort", role, "name",status);
        userDatabase.put(user.email(), user);
        //System.out.println(user.email());
    }

    @Override
    public User findUserByEmail(String email) {
        return null;
    }

   /* @Override
    public User findUserByEmail(String email) {
        return userDatabase.get(email);
    }*/

//}
