package de.cityfeedback.feedbackverwaltung.ui.controller;


public record FeedbackRequest(String title, String content, Long citizenId, String category) {}
