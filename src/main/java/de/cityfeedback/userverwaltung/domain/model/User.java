package de.cityfeedback.userverwaltung.domain.model;

import de.cityfeedback.userverwaltung.domain.valueobject.*;


import java.util.Objects;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Entity
@Table(name = "USER")
public class User {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "USER_ID")
private UserId id;

@Column(name = "NAME")
private String userName;

@Column(name = "EMAIL")
private String email;

@Column(name = "PASSWORD")
private String password;

@Enumerated(EnumType.STRING)
@Column(name = "ROLE")
private Role role;

// parameterloser Konstruktor
public User() {}

    public User(UserId id, String email, String password, Role role, String userName) {
        this.id = id; // Konvertiere UserID zu Long
        this.email = email;
        this.password = password;
        this.role = role;
        this.userName = userName;

        //!!! Validieren
        //analog zu Feedback
        //Validation.validateFeedbackContent(this.content);
        //    Validation.validateFeedbackTitle(this.title);

    }



    // Getter und Setter (optional, falls benötigt)
    public String getEmail() {
    return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter und Setter (optional, falls benötigt)
    public String getPassword () {
        return this.password;
    }

    public void setPassword(String password) {
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
                && Objects.equals(userName, user.userName);
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
                + id.userId()
                + ", email="
                + email
                + ", password='"
                + password
                + '\''
                + ", role='"
                + role
                + '\''
                + ", userName="
                + userName
                + '}';
    }
}


