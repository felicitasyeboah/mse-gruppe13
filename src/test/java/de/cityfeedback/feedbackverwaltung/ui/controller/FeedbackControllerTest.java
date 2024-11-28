package de.cityfeedback.feedbackverwaltung.ui.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.cityfeedback.feedbackverwaltung.application.dto.ApiResponse;
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

    FeedbackRequest request =
        new FeedbackRequest("Issue", "Details of the issue", 1L, "Beschwerde");
    Feedback feedback = new Feedback();
    feedback.setId(1L);
    feedback.setCitizenId(new CitizenId(request.citizenId()));
    feedback.setCategory(FeedbackCategory.COMPLAINT);
    feedback.setTitle(request.title());
    feedback.setContent(request.content());

    when(feedbackService.createFeedback(
            Mockito.anyString(), Mockito.anyString(), Mockito.anyLong(), Mockito.any()))
        .thenReturn(feedback);

    // Perform the POST request
    mockMvc
        .perform(
            post("/feedback")
                .contentType("application/json")
                .content(
                    "{ \"title\": \"Issue\", \"content\": \"Details of the issue\", \"citizenId\": 1, \"category\": \"Beschwerde\" }"))
        .andExpect(status().isCreated())
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.message")
                .value("Feedback created successfully with ID: 1"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(feedback.getId()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.title").value(feedback.getTitle()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.content").value(feedback.getContent()));

    ResponseEntity<ApiResponse> response = feedbackController.createFeedback(request);

    // Check the response
    assert (response.getStatusCode()).equals(HttpStatus.CREATED);
    assert (response.getBody() != null);
    ApiResponse apiResponse = response.getBody();
    assert (apiResponse.getMessage()).equals("Feedback created successfully with ID: 1");
    assert (apiResponse.getData() == feedback);
  }
}
