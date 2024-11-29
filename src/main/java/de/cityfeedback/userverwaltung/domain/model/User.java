package de.cityfeedback.userverwaltung.domain.model;

import de.cityfeedback.userverwaltung.domain.valueobject.*;


import java.util.Objects;
import jakarta.persistence.*;

@Entity
public class User {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

@Embedded
private Email email;

@Embedded
private Password password;

@Enumerated(EnumType.STRING)
private Role role;

@Embedded
private UserName userName;

@Enumerated(EnumType.STRING)
private UserStatus userStatus;

// parameterloser Konstruktor
protected User() {}

    public User(UserID id, Email email, Password password, Role role, UserName userName, UserStatus userStatus) {
        this.id = id.id(); // Konvertiere UserID zu Long
        this.email = email;
        this.password = password;
        this.role = role;
        this.userName = userName;
        this.userStatus = userStatus;
    }

    // Getter und Setter (optional, falls benötigt)
    public Email getEmail() {
    return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    // Getter und Setter (optional, falls benötigt)
    public Password getPassword () {
        return this.password;
    }

    public void setPassword(Password password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id)
                && Objects.equals(email, user.email)
                && Objects.equals(password, user.password)
                && Objects.equals(role, user.role)
                && Objects.equals(userName, user.userName)
                && Objects.equals(userStatus, user.userStatus);

    }

    /*@Override
    public int hashCode() {
        return Objects.hash(
                id, category, title, content, citizenId, employeeId, comment, status, createdAt, updatedAt);
    }*/

    @Override
    public String toString() {
        return "User{"
                + "id="
                + id
                + ", email="
                + email.email()
                + ", password='"
                + password.password()
                + '\''
                + ", role='"
                + role
                + '\''
                + ", userName="
                + userName.username()
                + ", userStatus="
                + userStatus
                + '}';
    }
}


/*
public User(UserID id, UserName username, Email email, Role role) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.role = role;
}

public User(
        UserID userId,         // Eindeutige Identität
        String username,       // Benutzername
        String email,          // E-Mail-Adresse
        UserStatus status,      // Status des Benutzers (z. B. AKTIV, INAKTIV)
        Role role
)
{// Invariante validieren
    public User {
        Objects.requireNonNull(userId, "UserId darf nicht null sein");
        Objects.requireNonNull(username, "Username darf nicht null sein");
        Objects.requireNonNull(email, "Email darf nicht null sein");
        Objects.requireNonNull(status, "Status darf nicht null sein");

        if (!email.contains("@")) {
            throw new IllegalArgumentException("Email ist ungültig: " + email);
        }
    }

    // Fachliche Methoden
    public User activate() {
        if (status == UserStatus.AKTIV) {
            throw new IllegalStateException("User ist bereits aktiv.");
        }
        return new User(userId, username, email, UserStatus.AKTIV, Role.CITIZEN);
    }

    public User deactivate() {
        if (status == UserStatus.INAKTIV) {
            throw new IllegalStateException("User ist bereits inaktiv.");
        }
        return new User(userId, username, email, UserStatus.INAKTIV, Role.CITIZEN);
    }
}

/*public class User {

    private Long id;
    private String username;
    private String email;
    private String role;

    public User() {
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
*/