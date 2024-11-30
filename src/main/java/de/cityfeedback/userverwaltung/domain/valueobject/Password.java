package de.cityfeedback.userverwaltung.domain.valueobject;


import jakarta.persistence.Embeddable;

@Embeddable
public record Password(String password) {
    public Password {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty.");
        }

    }
}