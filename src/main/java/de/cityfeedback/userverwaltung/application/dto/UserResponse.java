package de.cityfeedback.userverwaltung.application.dto;

import de.cityfeedback.userverwaltung.domain.model.User;
import de.cityfeedback.userverwaltung.domain.valueobject.Role;


import java.time.LocalDateTime;

public record UserResponse(
        Long userId,
        String userName,
        String email,
        String password,
        Role role) {

    public static UserResponse fromUser(User user) {
        return new UserResponse(
                user.getId(),
                user.getUserName(),
                user.getPassword(),
                user.getEmail(),
                user.getRole());
    }
}
