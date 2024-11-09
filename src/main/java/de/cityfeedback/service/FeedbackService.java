package de.cityfeedback.service;

import de.cityfeedback.domain.Feedback;
import de.cityfeedback.domain.Status;
import de.cityfeedback.domain.User;
import de.cityfeedback.repository.FeedbackRepository;

import java.util.Date;

public class FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private Long idCounter = 5L;

    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    public void createFeedback(String title, String content, Long citizenId, Long categoryId) {
        Feedback feedback = new Feedback();
        //TODO Validate inputs title and content

        feedback.setId(idCounter++);
        feedback.setCategoryId(categoryId);
        feedback.setTitle(title);
        feedback.setContent(content);
        feedback.setCitizenId(citizenId);
        feedback.setStatusId(1L);
        feedback.setCreatedAt(new Date().toInstant());
        feedback.setUpdatedAt(new Date().toInstant());

        this.feedbackRepository.save(feedback);
        System.out.println("Feedback: " + feedback);
    }

    public void updateFeedbackStatus(Feedback feedback, Status status) {
        feedback.setStatusId(2L);
        this.feedbackRepository.save(feedback);
    }

    public void assignEmployeeToFeedback(Feedback feedback, User employee) {
        feedback.setEmployeeId(2L);
        feedbackRepository.save(feedback);
    }
}
