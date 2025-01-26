package de.cityfeedback.feedbackverwaltung.ui.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.cityfeedback.feedbackverwaltung.application.dto.FeedbackDto;
import de.cityfeedback.feedbackverwaltung.application.dto.FeedbackUpdateRequest;
import de.cityfeedback.feedbackverwaltung.application.services.FeedbackService;
import de.cityfeedback.feedbackverwaltung.domain.valueobject.FeedbackCategory;
import de.cityfeedback.feedbackverwaltung.domain.valueobject.FeedbackStatus;
import de.cityfeedback.shared.GlobalExceptionHandler;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
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
    mockMvc =
        MockMvcBuilders.standaloneSetup(feedbackController)
            .setControllerAdvice(new GlobalExceptionHandler())
            .build();
  }

  @Test
  void createFeedback_ShouldReturnCreatedResponse() throws Exception {
    // Arrange: Create a request object and expected feedback entity
    FeedbackDto request = new FeedbackDto("Issue", "Details of the issue", 1L, "Beschwerde");
    FeedbackDto createdFeedback =
        new FeedbackDto(
            1L,
            "Issue",
            "Details of the issue",
            "Beschwerde",
            1L,
            "John Doe",
            "john.doe@example.com",
            null,
            null,
            null,
            null,
            "OFFEN",
            LocalDateTime.now(),
            null);
    String expectedMessage = "Feedback created successfully with ID: " + createdFeedback.id();

    // Mock the service behavior
    when(feedbackService.createFeedback(
            anyString(), anyString(), anyLong(), any(FeedbackCategory.class)))
        .thenReturn(createdFeedback);

    // Act: Perform the POST request using MockMvc
    mockMvc
        .perform(
            post("/feedback")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.message").value(expectedMessage))
        .andExpect(jsonPath("$.data.id").value(createdFeedback.id()))
        .andExpect(jsonPath("$.data.title").value(createdFeedback.title()))
        .andExpect(jsonPath("$.data.content").value(createdFeedback.content()))
        .andExpect(jsonPath("$.data.category").value(createdFeedback.category()));

    // Verify that the service method was called
    verify(feedbackService, times(1))
        .createFeedback(
            request.title(), request.content(), request.citizenId(), FeedbackCategory.COMPLAINT);
  }

  @Test
  void createFeedback_ShouldReturnInternalServerError_WhenExceptionIsThrown() throws Exception {
    // Arrange: Create a request object
    FeedbackDto request = new FeedbackDto("Issue", "Details of the issue", 1L, "Beschwerde");

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
        .andExpect(status().isInternalServerError())
        .andExpect(jsonPath("$.message").value("Unerwarteter Fehler: Test exception"))
        .andExpect(jsonPath("$.data").isEmpty());

    // Verify that the service method was called
    verify(feedbackService, times(1))
        .createFeedback(
            Mockito.anyString(),
            Mockito.anyString(),
            Mockito.anyLong(),
            Mockito.any(FeedbackCategory.class));
  }

  @Test
  void updateFeedback_ShouldReturnSuccess_WhenFeedbackIsUpdated() throws Exception {
    Long feedbackId = 1L;
    FeedbackUpdateRequest request =
        new FeedbackUpdateRequest("Updated comment", 101L, "EMPLOYEE", "comment");
    FeedbackDto updatedFeedback =
        new FeedbackDto(
            feedbackId,
            "Issue",
            "Details of the issue",
            "Beschwerde",
            1L,
            null,
            null,
            101L,
            null,
            null,
            "Updated comment",
            FeedbackStatus.IN_PROGRESS.getStatusName(),
            null,
            null);

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
        .andExpect(jsonPath("$.message").value("Fehler: Invalid update type"));

    verify(feedbackService, times(1))
        .updateFeedback(feedbackId, "Updated comment", 101L, "EMPLOYEE", "invalid");
  }

  @Test
  void updateFeedback_ShouldReturnNotFound_WhenFeedbackNotFound() throws Exception {
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
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.message").value("Fehler: Feedback not found"));

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
                    "Fehler: Unauthorized to update this feedback. You are not the assigned employee or have not the role of an employee."));

    verify(feedbackService, times(1))
        .updateFeedback(feedbackId, "Updated comment", 102L, "EMPLOYEE", "comment");
  }

  @Test
  void getFeedbacksByUserId_ShouldReturnFeedbackList_WhenFeedbacksExist() throws Exception {
    Long citizenId = 1L;
    List<FeedbackDto> feedbackList =
        List.of(
            new FeedbackDto("Issue1", "Details of issue 1", citizenId, "Beschwerde"),
            new FeedbackDto("Issue2", "Details of issue 2", citizenId, "Anfrage"));
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
  void getFeedbacksByUserId_ShouldReturnInternalServerError_WhenExceptionIsThrown()
      throws Exception {
    Long citizenId = 1L;

    when(feedbackService.findAllFeedbacksForCitizen(citizenId))
        .thenThrow(new RuntimeException("Test exception"));

    mockMvc
        .perform(get("/feedback/user/" + citizenId).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isInternalServerError())
        .andExpect(jsonPath("$.message").value("Unerwarteter Fehler: Test exception"))
        .andExpect(jsonPath("$.data").isEmpty());

    verify(feedbackService, times(1)).findAllFeedbacksForCitizen(citizenId);
  }

  @Test
  void getOpenFeedbacks_ShouldReturnOpenFeedbackList_WhenFeedbacksExist() throws Exception {
    List<FeedbackDto> openFeedbacks =
        List.of(
            new FeedbackDto("Open Issue1", "Details of open issue 1", 1L, "Beschwerde"),
            new FeedbackDto("Open Issue2", "Details of open issue 2", 2L, "Anfrage"));

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
  void getOpenFeedbacks_ShouldReturnInternalServerError_WhenExceptionIsThrown() throws Exception {
    when(feedbackService.findAllOpenFeedbacks()).thenThrow(new RuntimeException("Test exception"));

    mockMvc
        .perform(get("/feedback/all-open").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isInternalServerError())
        .andExpect(jsonPath("$.message").value("Unerwarteter Fehler: Test exception"))
        .andExpect(jsonPath("$.data").isEmpty());

    verify(feedbackService, times(1)).findAllOpenFeedbacks();
  }

  @Test
  void getFeedbackById_ShouldReturnFeedback_WhenFeedbackExists() throws Exception {
    Long feedbackId = 1L;
    FeedbackDto feedbackDto =
        new FeedbackDto(
            feedbackId,
            "Issue",
            "Details of the issue",
            "Beschwerde",
            1L,
            null,
            null,
            null,
            null,
            null,
            null,
            "NEW",
            null,
            null);

    when(feedbackService.getFeedbackById(feedbackId)).thenReturn(feedbackDto);

    mockMvc
        .perform(get("/feedback/" + feedbackId).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.title").value(feedbackDto.title()))
        .andExpect(jsonPath("$.data.content").value(feedbackDto.content()))
        .andExpect(jsonPath("$.data.category").value(feedbackDto.category()));

    verify(feedbackService, times(1)).getFeedbackById(feedbackId);
  }

  @Test
  void getFeedbackById_ShouldReturnInternalServerError_WhenExceptionIsThrown() throws Exception {
    Long feedbackId = 1L;

    when(feedbackService.getFeedbackById(feedbackId))
        .thenThrow(new RuntimeException("Test exception"));

    mockMvc
        .perform(get("/feedback/" + feedbackId).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isInternalServerError())
        .andExpect(jsonPath("$.message").value("Unerwarteter Fehler: Test exception"))
        .andExpect(jsonPath("$.data").isEmpty());

    verify(feedbackService, times(1)).getFeedbackById(feedbackId);
  }
}
