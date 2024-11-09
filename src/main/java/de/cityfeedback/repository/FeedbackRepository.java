package de.cityfeedback.repository;

import de.cityfeedback.domain.Feedback;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface FeedbackRepository extends Repository<Feedback, Long> {

    void save(Feedback feedback);

    Feedback findById(Long id);

    List<Feedback> findAll();
}
