package de.cityfeedback.feedbackverwaltung.ui.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.cityfeedback.feedbackverwaltung.application.dto.ApiResponse;
import de.cityfeedback.feedbackverwaltung.application.dto.FeedbackCreateRequest;
import de.cityfeedback.feedbackverwaltung.application.dto.FeedbackResponse;
import de.cityfeedback.feedbackverwaltung.application.services.FeedbackService;
import de.cityfeedback.feedbackverwaltung.domain.model.Feedback;
import de.cityfeedback.feedbackverwaltung.domain.valueobject.CitizenId;
import de.cityfeedback.feedbackverwaltung.domain.valueobject.FeedbackCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class) // This is the key to initialize mocks properly
class FeedbackControllerTest {

  @Mock private FeedbackService feedbackService;

  @InjectMocks private FeedbackController feedbackController;

  private MockMvc mockMvc;

  @BeforeEach
  void setup() {
    // Initialize MockMvc with standalone setup for testing the controller
    mockMvc = MockMvcBuilders.standaloneSetup(feedbackController).build();
  }

  @Test
  void createFeedback_ShouldReturnCreatedResponse() throws Exception {
    // Arrange: Create a request object and expected feedback entity
    FeedbackCreateRequest request =
        new FeedbackCreateRequest("Issue", "Details of the issue", 1L, null, "Beschwerde", null);

    Feedback feedback = new Feedback();
    feedback.setId(1L);
    feedback.setCitizenId(new CitizenId(request.citizenId()));
    feedback.setCategory(FeedbackCategory.COMPLAINT);
    feedback.setTitle(request.title());
    feedback.setContent(request.content());

    FeedbackResponse expectedResponse = FeedbackResponse.fromFeedback(feedback);
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
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(expectedMessage))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(feedback.getId()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.title").value(feedback.getTitle()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.content").value(feedback.getContent()))
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.data.categoryName")
                .value(feedback.getCategory().getCategoryName()));

    // Act: Call the controller method directly
    ResponseEntity<ApiResponse> response = feedbackController.createFeedback(request);

    // Assert: Validate the response
    assertNotNull(response);
    assertEquals(HttpStatus.CREATED, response.getStatusCode());

    ApiResponse actualApiResponse = response.getBody();
    assertNotNull(actualApiResponse);
    assertEquals(expectedMessage, actualApiResponse.getMessage());
    assertEquals(expectedResponse.id(), ((FeedbackResponse) actualApiResponse.getData()).id());
    assertEquals(
        expectedResponse.title(), ((FeedbackResponse) actualApiResponse.getData()).title());
    assertEquals(
        expectedResponse.content(), ((FeedbackResponse) actualApiResponse.getData()).content());
    assertEquals(
        expectedResponse.categoryName(),
        ((FeedbackResponse) actualApiResponse.getData()).categoryName());
  }
}
