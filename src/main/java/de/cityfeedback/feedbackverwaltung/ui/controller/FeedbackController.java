package de.cityfeedback.feedbackverwaltung.ui.controller;

import de.cityfeedback.feedbackverwaltung.application.dto.FeedbackDto;
import de.cityfeedback.feedbackverwaltung.application.dto.FeedbackUpdateRequest;
import de.cityfeedback.feedbackverwaltung.application.services.FeedbackService;
import de.cityfeedback.shared.dto.ApiResponse;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {

  private final FeedbackService feedbackService;

  public FeedbackController(FeedbackService feedbackService) {
    this.feedbackService = feedbackService;
  }

  @PostMapping
  public ResponseEntity<ApiResponse> createFeedback(@RequestBody FeedbackDto request) {
    String title = request.title();
    String content = request.content();
    Long citizenId = request.citizenId();

    FeedbackDto createdFeedback =
        feedbackService.createFeedback(title, content, citizenId, request.category());

    String message = "Feedback created successfully with ID: " + createdFeedback.id();

    ApiResponse response = new ApiResponse(message, createdFeedback);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  /**
   * Get all feedbacks for a specific citizen (user)
   *
   * @param citizenId Long id of the citizen
   * @return List<Feedback>
   */
  @GetMapping("/user/{citizenId}")
  public ResponseEntity<ApiResponse> getFeedbacksByUserId(@PathVariable Long citizenId) {
    List<FeedbackDto> feedbacks = feedbackService.findAllFeedbacksForCitizen(citizenId);
    return ResponseEntity.ok(new ApiResponse(null, feedbacks));
  }

  @PatchMapping("/{feedbackId}")
  public ResponseEntity<ApiResponse> updateFeedback(
      @PathVariable Long feedbackId, @RequestBody FeedbackUpdateRequest request) {

    FeedbackDto updatedFeedback =
        feedbackService.updateFeedback(
            feedbackId,
            request.comment(),
            request.userId(),
            request.userRole(),
            request.updateType());

    // Create the response with message
    ApiResponse response = new ApiResponse("Feedback updated successfully", updatedFeedback);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("/{feedbackId}")
  public ResponseEntity<ApiResponse> getFeedbackById(@PathVariable Long feedbackId) {
    FeedbackDto feedbackDto = feedbackService.getFeedbackById(feedbackId);
    return ResponseEntity.ok(new ApiResponse(null, feedbackDto));
  }

  @GetMapping("/all-open")
  public ResponseEntity<ApiResponse> getOpenFeedbacks() {
    List<FeedbackDto> openFeedbacks = feedbackService.findAllOpenFeedbacks();
    return ResponseEntity.ok(new ApiResponse(null, openFeedbacks));
  }
}
