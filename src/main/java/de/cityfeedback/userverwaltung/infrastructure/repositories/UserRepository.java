package de.cityfeedback.userverwaltung.infrastructure.repositories;

import de.cityfeedback.userverwaltung.domain.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT us FROM User us WHERE LOWER(us.email) = LOWER(:email)")
    Optional<User> findUserByEmail(@Param("email") String email);

    Optional<User> findByEmail(String email);



   // @Query(value = "SELECT * FROM USERS WHERE EMAIL_ADDRESS = ?1", nativeQuery = true)
   // Optional<User> findByEmailAddress(String emailAddress);

   // @Query("SELECT us FROM User us WHERE us.EMAIL = :email AND us.PASSWORD = :password")
   // Optional<User> findUserByEmailAndPassword(@Param("email") String email, @Param("password") String password);

}