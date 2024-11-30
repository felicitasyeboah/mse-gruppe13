package de.cityfeedback.userverwaltung.domain.valueobject;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public record UserID (long id) {

    public UserID {
        Objects.requireNonNull(id, "Id darf nicht null sein");
        //if (id  null) {
        //    throw new IllegalArgumentException("Id darf nicht leer sein");
        //}


    }
}


