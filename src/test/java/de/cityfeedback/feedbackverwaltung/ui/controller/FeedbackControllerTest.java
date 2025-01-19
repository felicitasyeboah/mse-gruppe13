package de.cityfeedback.feedbackverwaltung.ui.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.cityfeedback.feedbackverwaltung.application.dto.ApiResponse;
import de.cityfeedback.feedbackverwaltung.application.dto.FeedbackDto;
import de.cityfeedback.feedbackverwaltung.application.dto.FeedbackUpdateRequest;
import de.cityfeedback.feedbackverwaltung.application.services.FeedbackService;
import de.cityfeedback.feedbackverwaltung.domain.model.Feedback;
import de.cityfeedback.feedbackverwaltung.domain.valueobject.CitizenId;
import de.cityfeedback.feedbackverwaltung.domain.valueobject.FeedbackCategory;
import de.cityfeedback.feedbackverwaltung.domain.valueobject.FeedbackStatus;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class) // This is the key to initialize mocks properly
class FeedbackControllerTest {

  @Mock private FeedbackService feedbackService;

  @InjectMocks private FeedbackController feedbackController;

  private MockMvc mockMvc;

  private final ObjectMapper objectMapper = new ObjectMapper();

  @BeforeEach
  void setup() {
    // Initialize MockMvc with standalone setup for testing the controller
    mockMvc = MockMvcBuilders.standaloneSetup(feedbackController).build();
  }

  @Test
  void createFeedback_ShouldReturnCreatedResponse() throws Exception {
    // Arrange: Create a request object and expected feedback entity
    FeedbackDto request =
        new FeedbackDto("Issue", "Details of the issue", 1L, null, "Beschwerde", null);

    Feedback feedback = new Feedback();
    feedback.setId(1L);
    feedback.setCitizenId(new CitizenId(request.citizenId()));
    feedback.setCategory(FeedbackCategory.fromCategoryName(request.category()));
    feedback.setTitle(request.title());
    feedback.setContent(request.content());

    FeedbackDto expectedResponse = FeedbackDto.fromFeedback(feedback);
    String expectedMessage = "Feedback created successfully with ID: " + feedback.getId();

    // Mock the service behavior
    when(feedbackService.createFeedback(
            Mockito.anyString(),
            Mockito.anyString(),
            Mockito.anyLong(),
            Mockito.any(FeedbackCategory.class)))
        .thenReturn(feedback);

    // Act: Perform the POST request using MockMvc
    mockMvc
        .perform(
            post("/feedback")
                .contentType("application/json")
                .content(
                    "{ \"title\": \"Issue\", \"content\": \"Details of the issue\", \"citizenId\": 1, \"category\": \"Beschwerde\" }"))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.message").value(expectedMessage))
        .andExpect(jsonPath("$.data.id").value(feedback.getId()))
        .andExpect(jsonPath("$.data.title").value(feedback.getTitle()))
        .andExpect(jsonPath("$.data.content").value(feedback.getContent()))
        .andExpect(jsonPath("$.data.category").value(feedback.getCategory().getCategoryName()));

    // Act: Call the controller method directly
    ResponseEntity<ApiResponse> response = feedbackController.createFeedback(request);

    // Assert: Validate the response
    assertNotNull(response);
    assertEquals(HttpStatus.CREATED, response.getStatusCode());

    ApiResponse actualApiResponse = response.getBody();
    assertNotNull(actualApiResponse);
    assertEquals(expectedMessage, actualApiResponse.getMessage());
    assertEquals(expectedResponse.id(), ((FeedbackDto) actualApiResponse.getData()).id());
    assertEquals(expectedResponse.title(), ((FeedbackDto) actualApiResponse.getData()).title());
    assertEquals(expectedResponse.content(), ((FeedbackDto) actualApiResponse.getData()).content());
    assertEquals(
        expectedResponse.category(), ((FeedbackDto) actualApiResponse.getData()).category());
  }

  @Test
  void updateFeedback_ShouldReturnSuccess_WhenFeedbackIsUpdated() throws Exception {
    Long feedbackId = 1L;
    FeedbackUpdateRequest request =
        new FeedbackUpdateRequest("Updated comment", 101L, "EMPLOYEE", "comment");
    Feedback updatedFeedback = new Feedback();
    updatedFeedback.setId(feedbackId);
    updatedFeedback.setComment("Updated comment");
    updatedFeedback.setCategory(FeedbackCategory.COMPLAINT);
    updatedFeedback.setCitizenId(new CitizenId(1L));
    updatedFeedback.setStatus(FeedbackStatus.IN_PROGRESS);

    when(feedbackService.updateFeedback(
            feedbackId,
            request.comment(),
            request.userId(),
            request.userRole(),
            request.updateType()))
        .thenReturn(updatedFeedback);

    mockMvc
        .perform(
            patch("/feedback/" + feedbackId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message").value("Feedback updated successfully"))
        .andExpect(jsonPath("$.data.comment").value("Updated comment"))
        .andExpect(jsonPath("$.data.status").value(FeedbackStatus.IN_PROGRESS.getStatusName()));

    verify(feedbackService, times(1))
        .updateFeedback(feedbackId, "Updated comment", 101L, "EMPLOYEE", "comment");
  }

  @Test
  void updateFeedback_ShouldReturnBadRequest_WhenInvalidUpdateTypeIsProvided() throws Exception {
    Long feedbackId = 1L;
    FeedbackUpdateRequest request =
        new FeedbackUpdateRequest("Updated comment", 101L, "EMPLOYEE", "invalid");

    when(feedbackService.updateFeedback(
            feedbackId,
            request.comment(),
            request.userId(),
            request.userRole(),
            request.updateType()))
        .thenThrow(new IllegalArgumentException("Invalid update type"));

    mockMvc
        .perform(
            patch("/feedback/" + feedbackId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("Error updating feedback. - Invalid update type"));

    verify(feedbackService, times(1))
        .updateFeedback(feedbackId, "Updated comment", 101L, "EMPLOYEE", "invalid");
  }

  @Test
  void updateFeedback_ShouldReturnBadRequest_WhenFeedbackNotFound() throws Exception {
    Long feedbackId = 1L;
    FeedbackUpdateRequest request =
        new FeedbackUpdateRequest("Updated comment", 101L, "EMPLOYEE", "comment");

    when(feedbackService.updateFeedback(
            feedbackId,
            request.comment(),
            request.userId(),
            request.userRole(),
            request.updateType()))
        .thenThrow(new EntityNotFoundException("Feedback not found"));

    mockMvc
        .perform(
            patch("/feedback/" + feedbackId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("Error updating feedback. - Feedback not found"));

    verify(feedbackService, times(1))
        .updateFeedback(feedbackId, "Updated comment", 101L, "EMPLOYEE", "comment");
  }

  @Test
  void updateFeedback_ShouldReturnBadRequest_WhenUnauthorizedUserTriesToUpdate() throws Exception {
    Long feedbackId = 1L;
    FeedbackUpdateRequest request =
        new FeedbackUpdateRequest("Updated comment", 102L, "EMPLOYEE", "comment");

    when(feedbackService.updateFeedback(
            feedbackId,
            request.comment(),
            request.userId(),
            request.userRole(),
            request.updateType()))
        .thenThrow(
            new IllegalArgumentException(
                "Unauthorized to update this feedback. You are not the assigned employee or have not the role of an employee."));

    mockMvc
        .perform(
            patch("/feedback/" + feedbackId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest())
        .andExpect(
            jsonPath("$.message")
                .value(
                    "Error updating feedback. - Unauthorized to update this feedback. You are not the assigned employee or have not the role of an employee."));

    verify(feedbackService, times(1))
        .updateFeedback(feedbackId, "Updated comment", 102L, "EMPLOYEE", "comment");
  }
}
