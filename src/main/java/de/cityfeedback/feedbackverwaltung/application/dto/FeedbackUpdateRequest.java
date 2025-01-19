package de.cityfeedback.feedbackverwaltung.application.dto;

public record FeedbackUpdateRequest(
    String comment, Long userId, String userRole, String updateType) {}
