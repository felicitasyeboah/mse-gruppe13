package de.cityfeedback.feedbackverwaltung.application.dto;

public record FeedbackCreateRequest(
    String title,
    String content,
    Long citizenId,
    Long employeeId,
    String category,
    String comment) {}
