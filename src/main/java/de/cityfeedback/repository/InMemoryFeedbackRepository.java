package de.cityfeedback.repository;

import de.cityfeedback.domain.Feedback;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryFeedbackRepository implements FeedbackRepository {
    private final Map<Long, Feedback> feedbackDatabase = new HashMap<>();

    public InMemoryFeedbackRepository() {
        initFeedbackDb();
    }

    private void initFeedbackDb() {
        Feedback feedback = new Feedback();
        feedback.setId(1L);
        feedback.setCategoryId(2L);
        feedback.setTitle("das ist ein Testitel");
        feedback.setContent("Das ist der sehr aussagekraeftige Feedbacktitel");
        feedback.setCitizenId(1L);
        feedback.setStatusId(1L);
        feedback.setEmployeeId(100L);
        feedback.setCreatedAt(new Date().toInstant());
        feedback.setUpdatedAt(new Date().toInstant());
        feedbackDatabase.put(feedback.getId(), feedback);
    }

    @Override
    public Feedback save(Feedback feedback) {
        System.out.println("feedback in save: " + feedback);
        feedbackDatabase.put(feedback.getId(), feedback);
        return feedbackDatabase.put(feedback.getId(), feedback);

    }

    @Override
    public Feedback findById(Long id) {
        return feedbackDatabase.get(id);
    }

    @Override
    public List<Feedback> findAll() {
        return feedbackDatabase.values().stream().toList();
    }
}
