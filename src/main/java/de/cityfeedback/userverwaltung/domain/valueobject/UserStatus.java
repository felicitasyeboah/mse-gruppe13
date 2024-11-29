package de.cityfeedback.userverwaltung.domain.valueobject;

import jakarta.persistence.Embeddable;

@Embeddable
public enum UserStatus {
    AKTIV,
    INAKTIV
}
