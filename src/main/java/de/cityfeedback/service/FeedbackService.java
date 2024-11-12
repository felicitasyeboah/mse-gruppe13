package de.cityfeedback.service;

import de.cityfeedback.domain.Feedback;
import de.cityfeedback.repository.InMemoryFeedbackRepository;
import de.cityfeedback.validator.Validation;

import java.util.Date;

public class FeedbackService {
    public final InMemoryFeedbackRepository feedbackRepository;
    private static Long idCounter = 2L;

    public FeedbackService(InMemoryFeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    public Feedback createFeedback(String title, String content, Long citizenId, Long categoryId) {
        Validation.validateComplaintTitle(title);
        Validation.validateComplaintDescription(content);

        Feedback feedback = new Feedback(categoryId, title, content, citizenId);
        feedback.setId(idCounter++); // for in memory repository, can be deleted when working with persistence db and auto increment
        return this.feedbackRepository.save(feedback);
    }

    public Feedback updateFeedbackStatus(Feedback feedback, Long statusId) {
        feedback.setStatusId(statusId);
        feedback.setUpdatedAt(new Date().toInstant());
        return this.feedbackRepository.save(feedback);
    }

    public Feedback assignEmployeeToFeedback(Feedback feedback, Long employeeId) {
        feedback.setEmployeeId(employeeId);
        feedback.setUpdatedAt(new Date().toInstant());
        return feedbackRepository.save(feedback);
    }

    public Feedback addCommentToFeedback(Feedback feedback, String comment) {
        feedback.setComment(comment);
        feedback.setUpdatedAt(new Date().toInstant());
        return feedbackRepository.save(feedback);
    }

    // TODO show feedbacks for user
}
