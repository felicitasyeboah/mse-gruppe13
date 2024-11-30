package de.cityfeedback.userverwaltung.domain.valueobject;


import jakarta.persistence.Embeddable;

@Embeddable
public record UserName(String username) {
    public UserName {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty.");
        }
    }
}




