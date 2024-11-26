package de.cityfeedback.feedbackverwaltung.infrastructure.repositories;

import de.cityfeedback.feedbackverwaltung.domain.model.Feedback;
import de.cityfeedback.feedbackverwaltung.domain.valueobject.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryFeedbackRepository {
  private final Map<Long, Feedback> feedbackDatabase = new HashMap<>();

  public InMemoryFeedbackRepository() {
    initFeedbackDb();
  }

  private void initFeedbackDb() {
    Feedback feedback = new Feedback();
    feedback.setId(1L);
    feedback.setCategory(CategoryEnum.REQUEST);
    feedback.setTitle(new Title("das ist ein Testitel"));
    feedback.setContent(new Content("Das ist der sehr aussagekraeftige Feedbacktitel"));
    feedback.setCitizenId(new CitizenId(1L));
    feedback.setStatus(StatusEnum.NEW);
    feedback.setEmployeeId(new EmployeeId(10L));
    feedback.setCreatedAt(new CreatedAt(new Date().toInstant()));
    feedback.setUpdatedAt(new UpdatedAt(new Date().toInstant()));
    feedbackDatabase.put(feedback.getId(), feedback);
  }

  public Feedback save(Feedback feedback) {
    System.out.println("feedback in save: " + feedback);
    feedbackDatabase.put(feedback.getId(), feedback);
    return feedbackDatabase.put(feedback.getId(), feedback);
  }

  public Feedback findById(Long id) {
    return feedbackDatabase.get(id);
  }

  public List<Feedback> findAll() {
    return feedbackDatabase.values().stream().toList();
  }
}
