package de.cityfeedback.feedbackverwaltung.application.services;

import de.cityfeedback.feedbackverwaltung.application.dto.FeedbackDto;
import de.cityfeedback.feedbackverwaltung.application.dto.UserDto;
import de.cityfeedback.feedbackverwaltung.domain.events.FeedbackCreatedEvent;
import de.cityfeedback.feedbackverwaltung.domain.model.Feedback;
import de.cityfeedback.feedbackverwaltung.domain.valueobject.*;
import de.cityfeedback.feedbackverwaltung.infrastructure.repositories.FeedbackRepository;
import de.cityfeedback.shared.events.FeedbackUpdatedEvent;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FeedbackService {
  private final FeedbackRepository feedbackRepository;
  public final ApplicationEventPublisher eventPublisher;
  private final UserServiceClient userServiceClient;

  public FeedbackService(
      FeedbackRepository feedbackRepository,
      ApplicationEventPublisher eventPublisher,
      UserServiceClient userServiceClient) {
    this.feedbackRepository = feedbackRepository;
    this.eventPublisher = eventPublisher;
    this.userServiceClient = userServiceClient;
  }

  @Transactional
  public FeedbackDto createFeedback(
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

    UserDto citizen = userServiceClient.fetchUserById(citizenId);

    return FeedbackDto.of(feedback, citizen, null);
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

  public List<FeedbackDto> findAllFeedbacksForCitizen(Long citizenId) {
    return feedbackRepository.findAllByCitizenId(citizenId).stream()
        .map(
            feedback -> {
              UserDto citizen =
                  userServiceClient.fetchUserById(feedback.getCitizenId().citizenId());
              UserDto employee =
                  feedback.getEmployeeId() != null
                      ? userServiceClient.fetchUserById(feedback.getEmployeeId().employeeId())
                      : null;
              return FeedbackDto.of(feedback, citizen, employee);
            })
        .toList();
  }

  @Transactional
  public FeedbackDto updateFeedback(
      Long feedbackId, String comment, Long userId, String userRole, String updateType) {
    Feedback feedback =
        feedbackRepository
            .findById(feedbackId)
            .orElseThrow(() -> new EntityNotFoundException("Feedback not found"));

    // Check if the employeeId matches the employeeId from the requested userId
    if (feedback.getEmployeeId() != null && !feedback.getEmployeeId().employeeId().equals(userId)
        || !userRole.equals("EMPLOYEE")) {
      throw new IllegalArgumentException(
          "Unauthorized to update this feedback. You are not the assigned employee or have not the role of an employee.");
    }

    // Update feedback based on updateType
    switch (updateType.toLowerCase()) {
      case "assign":
        feedback.assignToEmployee(new EmployeeId(userId));
        break;
      case "comment":
        feedback.addComment(comment);
        break;
      case "close":
        feedback.closeFeedback();
        break;
      default:
        throw new IllegalArgumentException("Invalid update type");
    }

    feedback = feedbackRepository.save(feedback);
    // Create the domain event
    FeedbackUpdatedEvent event =
        new FeedbackUpdatedEvent(
            feedback.getId(),
            feedback.getCitizenId().citizenId(),
            feedback.getEmployeeId().employeeId(),
            feedback.getTitle(),
            feedback.getUpdatedAt(),
            feedback.getStatus().getStatusName());
    // Publish the event
    eventPublisher.publishEvent(event);

    UserDto citizen = userServiceClient.fetchUserById(feedback.getCitizenId().citizenId());
    UserDto employee =
        feedback.getEmployeeId() != null
            ? userServiceClient.fetchUserById(feedback.getEmployeeId().employeeId())
            : null;
    return FeedbackDto.of(feedback, citizen, employee);
  }

  // find all feedbacks that are not in status closed
  public List<FeedbackDto> findAllOpenFeedbacks() {
    return feedbackRepository.findAllByStatusNot(FeedbackStatus.CLOSED).stream()
        .map(
            feedback -> {
              UserDto citizen =
                  userServiceClient.fetchUserById(feedback.getCitizenId().citizenId());
              UserDto employee =
                  feedback.getEmployeeId() != null
                      ? userServiceClient.fetchUserById(feedback.getEmployeeId().employeeId())
                      : null;
              return FeedbackDto.of(feedback, citizen, employee);
            })
        .toList();
  }

  public FeedbackDto getFeedbackById(Long feedbackId) {
    Feedback feedback =
        feedbackRepository
            .findById(feedbackId)
            .orElseThrow(() -> new EntityNotFoundException("Feedback not found"));
    UserDto citizen = userServiceClient.fetchUserById(feedback.getCitizenId().citizenId());
    UserDto employee =
        feedback.getEmployeeId() != null
            ? userServiceClient.fetchUserById(feedback.getEmployeeId().employeeId())
            : null;
    return FeedbackDto.of(feedback, citizen, employee);
  }
}
