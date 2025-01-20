package de.cityfeedback.feedbackverwaltung.ui.controller;

import de.cityfeedback.feedbackverwaltung.application.dto.ApiResponse;
import de.cityfeedback.feedbackverwaltung.application.dto.FeedbackDto;
import de.cityfeedback.feedbackverwaltung.application.dto.FeedbackUpdateRequest;
import de.cityfeedback.feedbackverwaltung.application.services.FeedbackService;
import de.cityfeedback.feedbackverwaltung.domain.model.Feedback;
import de.cityfeedback.feedbackverwaltung.domain.valueobject.FeedbackCategory;
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
  public ResponseEntity<ApiResponse> getFeedbacksByUserId(@PathVariable Long citizenId) {
    try {
      List<FeedbackDto> feedbacks = feedbackService.findAllFeedbacksForCitizen(citizenId);
      return ResponseEntity.ok(new ApiResponse(null, feedbacks));
    } catch (Exception e) {
      return ResponseEntity.badRequest()
          .body(new ApiResponse("Error retrieving feedbacks. - " + e.getMessage(), null));
    }
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

  @GetMapping("/{feedbackId}")
  public ResponseEntity<ApiResponse> getFeedbackById(@PathVariable Long feedbackId) {
    try {
      Feedback feedback = feedbackService.getFeedbackById(feedbackId);
      FeedbackDto feedbackDto = FeedbackDto.fromFeedback(feedback);
      return ResponseEntity.ok(new ApiResponse(null, feedbackDto));
    } catch (Exception e) {
      return ResponseEntity.badRequest()
          .body(new ApiResponse("Error retrieving feedback. - " + e.getMessage(), null));
    }
  }

  @GetMapping("/all-open")
  public ResponseEntity<ApiResponse> getOpenFeedbacks() {
    try {
      List<FeedbackDto> openFeedbacks = feedbackService.findAllOpenFeedbacks();
      return ResponseEntity.ok(new ApiResponse(null, openFeedbacks));
    } catch (Exception e) {
      return ResponseEntity.badRequest()
          .body(new ApiResponse("Error retrieving open feedbacks. - " + e.getMessage(), null));
    }
  }
}
