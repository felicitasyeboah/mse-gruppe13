package de.cityfeedback.feedbackverwaltung.ui.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
import java.util.List;
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
  void createFeedback_ShouldReturnBadRequest_WhenExceptionIsThrown() throws Exception {
    // Arrange: Create a request object
    FeedbackDto request =
        new FeedbackDto("Issue", "Details of the issue", 1L, null, "Beschwerde", null);

    // Mock the service to throw an exception
    when(feedbackService.createFeedback(
            Mockito.anyString(),
            Mockito.anyString(),
            Mockito.anyLong(),
            Mockito.any(FeedbackCategory.class)))
        .thenThrow(new RuntimeException("Test exception"));

    // Act: Perform the POST request using MockMvc
    mockMvc
        .perform(
            post("/feedback")
                .contentType("application/json")
                .content(
                    "{ \"title\": \"Issue\", \"content\": \"Details of the issue\", \"citizenId\": 1, \"category\": \"Beschwerde\" }"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("Error creating feedback. - Test exception"))
        .andExpect(jsonPath("$.data").isEmpty());

    // Act: Call the controller method directly
    ResponseEntity<ApiResponse> response = feedbackController.createFeedback(request);

    // Assert: Validate the response
    assertNotNull(response);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    ApiResponse actualApiResponse = response.getBody();
    assertNotNull(actualApiResponse);
    assertEquals("Error creating feedback. - Test exception", actualApiResponse.getMessage());
    assertNull(actualApiResponse.getData());
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

  @Test
  void getFeedbacksByUserId_ShouldReturnFeedbackList_WhenFeedbacksExist() throws Exception {
    Long citizenId = 1L;
    List<FeedbackDto> feedbackList =
        List.of(
            new FeedbackDto("Issue1", "Details of issue 1", citizenId, null, "Beschwerde", null),
            new FeedbackDto("Issue2", "Details of issue 2", citizenId, null, "Anfrage", null));

    when(feedbackService.findAllFeedbacksForCitizen(citizenId)).thenReturn(feedbackList);

    mockMvc
        .perform(get("/feedback/user/" + citizenId).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data[0].title").value("Issue1"))
        .andExpect(jsonPath("$.data[0].content").value("Details of issue 1"))
        .andExpect(jsonPath("$.data[0].category").value("Beschwerde"))
        .andExpect(jsonPath("$.data[1].title").value("Issue2"))
        .andExpect(jsonPath("$.data[1].content").value("Details of issue 2"))
        .andExpect(jsonPath("$.data[1].category").value("Anfrage"));

    verify(feedbackService, times(1)).findAllFeedbacksForCitizen(citizenId);
  }

  @Test
  void getFeedbacksByUserId_ShouldReturnBadRequest_WhenExceptionIsThrown() throws Exception {
    Long citizenId = 1L;

    when(feedbackService.findAllFeedbacksForCitizen(citizenId))
        .thenThrow(new RuntimeException("Test exception"));

    mockMvc
        .perform(get("/feedback/user/" + citizenId).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("Error retrieving feedbacks. - Test exception"))
        .andExpect(jsonPath("$.data").isEmpty());

    verify(feedbackService, times(1)).findAllFeedbacksForCitizen(citizenId);
  }

  @Test
  void getOpenFeedbacks_ShouldReturnOpenFeedbackList_WhenFeedbacksExist() throws Exception {
    List<FeedbackDto> openFeedbacks =
        List.of(
            new FeedbackDto("Open Issue1", "Details of open issue 1", 1L, null, "Beschwerde", null),
            new FeedbackDto("Open Issue2", "Details of open issue 2", 2L, null, "Anfrage", null));

    when(feedbackService.findAllOpenFeedbacks()).thenReturn(openFeedbacks);

    mockMvc
        .perform(get("/feedback/all-open").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data[0].title").value("Open Issue1"))
        .andExpect(jsonPath("$.data[0].content").value("Details of open issue 1"))
        .andExpect(jsonPath("$.data[0].category").value("Beschwerde"))
        .andExpect(jsonPath("$.data[1].title").value("Open Issue2"))
        .andExpect(jsonPath("$.data[1].content").value("Details of open issue 2"))
        .andExpect(jsonPath("$.data[1].category").value("Anfrage"));

    verify(feedbackService, times(1)).findAllOpenFeedbacks();
  }

  @Test
  void getOpenFeedbacks_ShouldReturnBadRequest_WhenExceptionIsThrown() throws Exception {
    when(feedbackService.findAllOpenFeedbacks()).thenThrow(new RuntimeException("Test exception"));

    mockMvc
        .perform(get("/feedback/all-open").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("Error retrieving open feedbacks. - Test exception"))
        .andExpect(jsonPath("$.data").isEmpty());

    verify(feedbackService, times(1)).findAllOpenFeedbacks();
  }

  @Test
  void getFeedbackById_ShouldReturnFeedback_WhenFeedbackExists() throws Exception {
    Long feedbackId = 1L;
    Feedback feedback = new Feedback();
    feedback.setId(feedbackId);
    feedback.setTitle("Issue");
    feedback.setContent("Details of the issue");
    feedback.setCitizenId(new CitizenId(1L));
    feedback.setCategory(FeedbackCategory.COMPLAINT);

    when(feedbackService.getFeedbackById(feedbackId)).thenReturn(feedback);
    FeedbackDto feedbackDto = FeedbackDto.fromFeedback(feedback);
    mockMvc
        .perform(get("/feedback/" + feedbackId).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.title").value(feedbackDto.title()))
        .andExpect(jsonPath("$.data.content").value(feedbackDto.content()))
        .andExpect(jsonPath("$.data.category").value(feedbackDto.category()));

    verify(feedbackService, times(1)).getFeedbackById(feedbackId);
  }

  @Test
  void getFeedbackById_ShouldReturnBadRequest_WhenExceptionIsThrown() throws Exception {
    Long feedbackId = 1L;

    when(feedbackService.getFeedbackById(feedbackId))
        .thenThrow(new RuntimeException("Test exception"));

    mockMvc
        .perform(get("/feedback/" + feedbackId).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("Error retrieving feedback. - Test exception"))
        .andExpect(jsonPath("$.data").isEmpty());

    verify(feedbackService, times(1)).getFeedbackById(feedbackId);
  }
}
