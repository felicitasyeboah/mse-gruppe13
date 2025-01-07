package de.cityfeedback.feedbackverwaltung.ui.controller;

import de.cityfeedback.feedbackverwaltung.application.dto.ApiResponse;
import de.cityfeedback.feedbackverwaltung.application.dto.FeedbackCreateRequest;
import de.cityfeedback.feedbackverwaltung.application.dto.FeedbackResponse;
import de.cityfeedback.feedbackverwaltung.application.dto.FeedbackUpdateRequest;
import de.cityfeedback.feedbackverwaltung.application.services.FeedbackService;
import de.cityfeedback.feedbackverwaltung.domain.model.Feedback;
import de.cityfeedback.feedbackverwaltung.domain.valueobject.FeedbackCategory;
import de.cityfeedback.feedbackverwaltung.domain.valueobject.FeedbackStatus;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/feedback")
public class FeedbackController {

  private final FeedbackService feedbackService;

  public FeedbackController(FeedbackService feedbackService) {
    this.feedbackService = feedbackService;
  }

  @GetMapping("/data")
  public ResponseEntity<String> getData() {
    return ResponseEntity.ok("Daten vom Backend");
  }

  @PostMapping
  public ResponseEntity<ApiResponse> createFeedback(@RequestBody FeedbackCreateRequest request) {
    try {
      String title = request.title();
      String content = request.content();
      Long citizenId = request.citizenId();
      FeedbackCategory category = FeedbackCategory.fromCategoryName(request.category());

      Feedback createdFeedback =
          feedbackService.createFeedback(title, content, citizenId, category);
      FeedbackResponse feedbackResponse = FeedbackResponse.fromFeedback(createdFeedback);

      // Create the response message
      String message = "Feedback created successfully with ID: " + createdFeedback.getId();

      // Return response to the user
      ApiResponse response = new ApiResponse(message, feedbackResponse);
      return new ResponseEntity<>(response, HttpStatus.CREATED);
    } catch (Exception e) {
      ApiResponse response = new ApiResponse("Error creating feedback. - " + e.getMessage(), null);
      return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
  }

  /**
   * Get all feedbacks for a specific citizen (user)
   *
   * @param citizenId Long id of the citizen
   * @return List<Feedback>
   */
  @GetMapping("/user/{citizenId}")
  public List<Feedback> getFeedbacksByUserId(@PathVariable Long citizenId) {
    return feedbackService.findAllFeedbacksForCitizen(citizenId);
  }

  @PatchMapping("/{feedbackId}")
  public ResponseEntity<ApiResponse> updateFeedback(
      @PathVariable Long feedbackId, @RequestBody FeedbackUpdateRequest request) {
    try {
      Feedback updatedFeedback =
          feedbackService.updateFeedback(
              feedbackId,
              request.comment(),
              request.userId(),
              request.userRole(),
              request.updateType());
      FeedbackResponse feedbackResponse = FeedbackResponse.fromFeedback(updatedFeedback);

      // Create the response with message
      ApiResponse response = new ApiResponse("Feedback updated successfully", feedbackResponse);

      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(
          new ApiResponse("Error updating feedback. - " + e.getMessage(), null),
          HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping("/all-open")
  public List<Feedback> getOpenFeedbacks() {
    return feedbackService.findAllOpenFeedbacks();
  }

  /*@GetMapping("/{id}")
  public ResponseEntity<Feedback> getFeedbackById(@PathVariable Long id) {
     Optional<Feedback> feedback = feedbackService.findById(id);
    if (feedback.isPresent()) {
      return ResponseEntity.ok(feedback.get());
    } else {
      return ResponseEntity.notFound().build();
    }
  }*/
}
