package de.cityfeedback.feedbackverwaltung.application.dto;

public record FeedbackUpdateRequest(
    String comment, String updateType, Long userId, String userRole) {}
