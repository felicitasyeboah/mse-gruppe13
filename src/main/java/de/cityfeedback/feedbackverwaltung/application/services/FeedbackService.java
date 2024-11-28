package de.cityfeedback.feedbackverwaltung.application.services;

import de.cityfeedback.feedbackverwaltung.domain.events.FeedbackCreatedEvent;
import de.cityfeedback.feedbackverwaltung.domain.model.Feedback;
import de.cityfeedback.feedbackverwaltung.domain.valueobject.*;
import de.cityfeedback.feedbackverwaltung.infrastructure.repositories.FeedbackRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FeedbackService {
  private final FeedbackRepository feedbackRepository;
  public final ApplicationEventPublisher eventPublisher;

  public FeedbackService(
      FeedbackRepository feedbackRepository, ApplicationEventPublisher eventPublisher) {
    this.feedbackRepository = feedbackRepository;
    this.eventPublisher = eventPublisher;
  }

  @Transactional
  public Feedback createFeedback(
      String title, String content, Long citizenId, FeedbackCategory category) {

    Feedback feedback = new Feedback(title, content, category, new CitizenId(citizenId));
    feedback = this.feedbackRepository.save(feedback);

    // Create the domain event
    FeedbackCreatedEvent event =
        new FeedbackCreatedEvent(
            feedback.getId(),
            feedback.getCategory().getCategoryName(),
            feedback.getTitle(),
            feedback.getContent(),
            feedback.getCitizenId().citizenId(),
            feedback.getStatus().getStatusName(),
            feedback.getCreatedAt());

    // Publish the event
    eventPublisher.publishEvent(event);

    return feedback;
  }

  public Feedback updateFeedbackStatus(Long feedbackId, FeedbackStatus status) {
    Feedback feedback =
        feedbackRepository
            .findById(feedbackId)
            .orElseThrow(() -> new EntityNotFoundException("Feedback not found"));
    feedback.updateStatus(status);
    return feedbackRepository.save(feedback);
  }

  public Feedback assignFeedbackToEmployee(Long feedbackId, Long employeeId) {
    Feedback feedback =
        feedbackRepository
            .findById(feedbackId)
            .orElseThrow(() -> new EntityNotFoundException("Feedback not found"));
    feedback.assignToEmployee(new EmployeeId(employeeId));
    return feedbackRepository.save(feedback);
  }

  public Feedback addCommentToFeedback(Long feedbackId, String comment) {
    Feedback feedback =
        feedbackRepository
            .findById(feedbackId)
            .orElseThrow(() -> new EntityNotFoundException("Feedback not found"));
    feedback.addComment(comment);
    return feedbackRepository.save(feedback);
  }

  public List<Feedback> findAllFeedbacksForCitizen(Long citizenId) {
    System.out.println("all by userid " + feedbackRepository.findAllByCitizenId(citizenId));
    return feedbackRepository.findAllByCitizenId(citizenId);
  }
}
