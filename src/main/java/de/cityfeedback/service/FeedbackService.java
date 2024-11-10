package de.cityfeedback.service;

import de.cityfeedback.domain.Feedback;
import de.cityfeedback.repository.InMemoryFeedbackRepository;
import de.cityfeedback.validator.Validation;

import java.util.Date;

public class FeedbackService {
    private final InMemoryFeedbackRepository feedbackRepository;
    private static Long idCounter = 2L;

    public FeedbackService(InMemoryFeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    public Feedback createFeedback(String title, String content, Long citizenId, Long categoryId) {
        Feedback feedback = new Feedback();
        Validation.validateComplaintTitle(title);
        Validation.validateComplaintDescription(content);

        feedback.setCategoryId(categoryId);
        feedback.setTitle(title);
        feedback.setContent(content);
        feedback.setCitizenId(citizenId);
        feedback.setId(idCounter++);
        feedback.setStatusId(1L);
        feedback.setCreatedAt(new Date().toInstant());
        feedback.setUpdatedAt(new Date().toInstant());

        System.out.println("Feedback aha: " + feedback.toString());
        return this.feedbackRepository.save(feedback);
    }

    public Feedback updateFeedbackStatus(Feedback feedback, Long statusId) {
        feedback.setStatusId(statusId);
        feedback.setUpdatedAt(new Date().toInstant());
        return this.feedbackRepository.save(feedback);
    }

    public Feedback assignEmployeeToFeedback(Feedback feedback, Long employeeId) {
        feedback.setEmployeeId(employeeId);
        return feedbackRepository.save(feedback);
    }
}
