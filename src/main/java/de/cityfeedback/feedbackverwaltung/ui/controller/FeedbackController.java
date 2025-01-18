package de.cityfeedback.feedbackverwaltung.ui.controller;

import de.cityfeedback.feedbackverwaltung.application.dto.ApiResponse;
import de.cityfeedback.feedbackverwaltung.application.dto.FeedbackDto;
import de.cityfeedback.feedbackverwaltung.application.dto.FeedbackUpdateRequest;
import de.cityfeedback.feedbackverwaltung.application.services.FeedbackService;
import de.cityfeedback.feedbackverwaltung.domain.model.Feedback;
import de.cityfeedback.feedbackverwaltung.domain.valueobject.FeedbackCategory;

import java.util.List;
import java.util.Optional;

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
    try {
      String title = request.title();
      String content = request.content();
      Long citizenId = request.citizenId();
      FeedbackCategory category = FeedbackCategory.fromCategoryName(request.category());

      Feedback createdFeedback =
          feedbackService.createFeedback(title, content, citizenId, category);
      FeedbackDto feedbackDTO = FeedbackDto.fromFeedback(createdFeedback);

      // Create the response message
      String message = "Feedback created successfully with ID: " + createdFeedback.getId();

      // Return response to the user
      ApiResponse response = new ApiResponse(message, feedbackDTO);
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
  public List<FeedbackDto> getFeedbacksByUserId(@PathVariable Long citizenId) {
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
      FeedbackDto feedbackDTO = FeedbackDto.fromFeedback(updatedFeedback);

      // Create the response with message
      ApiResponse response = new ApiResponse("Feedback updated successfully", feedbackDTO);

      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(
          new ApiResponse("Error updating feedback. - " + e.getMessage(), null),
          HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping("/all-open")
  public List<FeedbackDto> getOpenFeedbacks() {
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
