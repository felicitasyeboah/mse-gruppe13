package de.cityfeedback.feedbackverwaltung.application.services;

import de.cityfeedback.feedbackverwaltung.domain.model.Feedback;
import de.cityfeedback.feedbackverwaltung.domain.valueobject.*;
import de.cityfeedback.feedbackverwaltung.infrastructure.repositories.FeedbackRepository;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FeedbackService {
  public final FeedbackRepository feedbackRepository;

  public FeedbackService(FeedbackRepository feedbackRepository) {
    this.feedbackRepository = feedbackRepository;
  }

  @Transactional
  public Feedback createFeedback(String title, String content, Long citizenId, String category) {

    Feedback feedback =
        new Feedback(
            CategoryEnum.valueOf(category),
            new Title(title),
            new Content(content),
            new CitizenId(citizenId));

    return this.feedbackRepository.save(feedback);
  }

  public Feedback updateFeedbackStatus(Feedback feedback, StatusEnum status) {
    feedback.setStatus(status);
    return updateFeedback(feedback);
  }

  public Feedback assignEmployeeToFeedback(Feedback feedback, EmployeeId employeeId) {
    feedback.setEmployeeId(employeeId);
    return updateFeedback(feedback);
  }

  public Feedback addCommentToFeedback(Feedback feedback, Comment comment) {
    feedback.setComment(comment);
    return updateFeedback(feedback);
  }

  public List<Feedback> findAllFeedbacksForCitizen(Long citizenId) {
    System.out.println("all by userid " + feedbackRepository.findAllByCitizenId(citizenId));
    return feedbackRepository.findAllByCitizenId(citizenId);
  }

  private Feedback updateFeedback(Feedback feedback) {
    feedback.setUpdatedAt(new UpdatedAt(new Date().toInstant()));
    return feedbackRepository.save(feedback);
  }

  public Feedback findById(Long id) {
    return feedbackRepository.findById(id).orElseThrow();
  }
  // TODO show feedbacks for user
}
