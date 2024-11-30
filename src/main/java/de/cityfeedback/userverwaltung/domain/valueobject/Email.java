package de.cityfeedback.userverwaltung.domain.valueobject;

import de.cityfeedback.validator.Validation;

import jakarta.persistence.Embeddable;


@Embeddable
    public record Email(String email) {
    public Email {
        System.out.println(email);
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty.");
        }
    }
}


