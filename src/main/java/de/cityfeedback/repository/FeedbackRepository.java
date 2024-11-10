package de.cityfeedback.repository;

import de.cityfeedback.domain.Feedback;

import java.util.List;

public interface FeedbackRepository {

    Feedback save(Feedback feedback);

    Feedback findById(Long id);

    List<Feedback> findAll();
}
