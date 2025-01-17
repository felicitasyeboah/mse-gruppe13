package de.cityfeedback.userverwaltung.domain.model;

import de.cityfeedback.userverwaltung.domain.valueobject.*;
import jakarta.persistence.*;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "USER")
public class User {

  @Id
  @Column(name = "USER_ID")
  private long id;

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

  public User(long id, String email, String password, Role role, String userName) {
    this.id = id; // Konvertiere UserID zu Long
    this.email = email;
    this.password = password;
    this.role = role;
    this.userName = userName;
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
        + id
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
