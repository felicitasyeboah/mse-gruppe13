package de.cityfeedback.feedbackverwaltung.ui.controller;

import de.cityfeedback.feedbackverwaltung.application.services.FeedbackService;
import de.cityfeedback.feedbackverwaltung.domain.model.Feedback;
import de.cityfeedback.feedbackverwaltung.domain.valueobject.StatusEnum;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {

  private final FeedbackService feedbackService;

  public FeedbackController(FeedbackService feedbackService) {
    this.feedbackService = feedbackService;
  }

  @PostMapping
  public Feedback createFeedback(@RequestBody FeedbackRequest request) {
    String title = request.title();
    String content = request.content();
    Long citizenId = request.citizenId();
    String category = request.category();

    return feedbackService.createFeedback(title, content, citizenId, category);
  }

  @GetMapping("/user/{citizenId}")
  public List<Feedback> getFeedbacksByUserId(@PathVariable Long citizenId) {
    return feedbackService.findAllFeedbacksForCitizen(citizenId);
  }

  @PostMapping("/status/{feedbackId}")
  public Feedback updateStatus(@PathVariable Long feedbackId, @RequestParam String status) {
    return feedbackService.updateFeedbackStatus(
        feedbackService.findById(feedbackId), StatusEnum.valueOf(status));
  }
}
