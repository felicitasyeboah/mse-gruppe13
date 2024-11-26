package de.cityfeedback.feedbackverwaltung.application.services;

import static org.junit.jupiter.api.Assertions.*;

import de.cityfeedback.feedbackverwaltung.domain.model.Feedback;
import de.cityfeedback.feedbackverwaltung.domain.valueobject.*;
import de.cityfeedback.feedbackverwaltung.infrastructure.repositories.FeedbackRepository;
import de.cityfeedback.validator.Validation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FeedbackServiceTest {
  //  @Autowired private FeedbackService feedbackService;
  //
  //  @MockBean private FeedbackRepository feedbackRepository;
  //
  //  @BeforeEach
  //  public void setUp() {
  //    // Initialize any test data or mocks here
  //  }
  //
  //  @Test
  //  public void testCreateFeedback_ShouldCreateNewFeedback() {
  //
  //    // arrange
  //    String title = "Das ist ein Testtitel";
  //    String content = "Das ist ein Testcontent fuer unsere testcases.";
  //    Long citizenId = 1L;
  //    String category = CategoryEnum.REQUEST.name();
  //    Validation.validateComplaintTitle(title);
  //    Validation.validateComplaintContent(content);
  //
  //    // act
  //    FeedbackAggregate savedFeedback =
  //        feedbackService.createFeedback(title, content, citizenId, category);
  //
  //    // assert
  //    assertNotNull(savedFeedback);
  //    assertEquals(StatusEnum.NEW, savedFeedback.getStatus());
  //    assertNotNull(savedFeedback.getCreatedAt());
  //    assertNotNull(savedFeedback.getUpdatedAt());
  //    assertNull(savedFeedback.getComment());
  //    assertNull(savedFeedback.getEmployeeId());
  //  }
  @Autowired private FeedbackService feedbackService;

  @Autowired private FeedbackRepository feedbackRepository;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    feedbackService = new FeedbackService(feedbackRepository);
  }

  @Test
  void createFeedback_ShouldSaveAndReturnFeedback() {

    // Arrange
    String title = "Test Feedback";
    String content = "This is a test feedback content.";
    Long citizenId = 123L;
    String category = "COMPLAINT";

    // Act
    Feedback feedback = feedbackService.createFeedback(title, content, citizenId, category);

    // Assert
    assertNotNull(feedback);
    assertEquals(title, feedback.getTitle().title());
    assertEquals(content, feedback.getContent().content());
    assertEquals(citizenId, feedback.getCitizenId().citizenId());
    assertEquals(CategoryEnum.valueOf(category), feedback.getCategory());
    assertEquals(StatusEnum.NEW, feedback.getStatus());

    // Verify that the feedback was saved to the database
    Feedback savedFeedback = feedbackRepository.findById(feedback.getId()).orElseThrow();
    assertEquals(feedback, savedFeedback);
  }

  //    // Arrange
  //    String title = "Test Feedback";
  //    String content = "This is a test feedback content.";
  //    Long citizenId = 123L;
  //    String category = "COMPLAINT";
  //
  //    FeedbackAggregate feedback =
  //        new FeedbackAggregate(
  //            CategoryEnum.valueOf(category),
  //            new Title(title),
  //            new Content(content),
  //            new CitizenId(citizenId));
  //    feedback.setId(1L);
  //
  //    when(feedbackRepository.save(any(FeedbackAggregate.class))).thenReturn(feedback);
  //
  //    // Act
  //    FeedbackAggregate result = feedbackService.createFeedback(title, content, citizenId,
  // category);
  //
  //    // Assert
  //    assertNotNull(result);
  //    assertEquals(1L, result.getId());
  //    assertEquals(title, result.getTitle().title());
  //    assertEquals(content, result.getContent().content());
  //    assertEquals(citizenId, result.getCitizenId().citizenId());
  //    assertEquals(CategoryEnum.COMPLAINT, result.getCategory());
  //    assertEquals(StatusEnum.NEW, result.getStatus());
  //
  //    // Verify repository interaction
  //    // verify(feedbackRepository, times(1)).save(feedback);
  //
  //    ArgumentCaptor<FeedbackAggregate> captor = ArgumentCaptor.forClass(FeedbackAggregate.class);
  //    verify(feedbackRepository).save(captor.capture());
  //    FeedbackAggregate capturedFeedback = captor.getValue();
  //
  //    assertEquals(title, capturedFeedback.getTitle().title());
  //    assertEquals(content, capturedFeedback.getContent().content());
  //    assertEquals(citizenId, capturedFeedback.getCitizenId().citizenId());
  //    assertEquals(CategoryEnum.COMPLAINT, capturedFeedback.getCategory());
  //    assertEquals(StatusEnum.NEW, capturedFeedback.getStatus());
  //
  //    verifyNoMoreInteractions(feedbackRepository);
  //  }

  @Test
  public void testUpdateFeedbackStatus_ShouldUpdateFeedbackStatusToInBearbeitung() {
    //    InMemoryFeedbackRepository feedbackRepository = new InMemoryFeedbackRepository();
    //    FeedbackService feedbackService = new FeedbackService(feedbackRepository);

    // arrange
    Feedback currentFeedback = feedbackRepository.findById(1L).orElseThrow();

    StatusEnum status = StatusEnum.IN_PROGRESS;

    // act
    Feedback updatedFeedback = feedbackService.updateFeedbackStatus(currentFeedback, status);
    // assert
    assertNotNull(updatedFeedback);
    assertEquals(status, updatedFeedback.getStatus());
  }

  @Test
  public void testAssignEmployeeToFeedback_ShouldAssignEmployee() {
    //    FeedbackRepository feedbackRepository = new FeedbackRepository();
    //    FeedbackService feedbackService = new FeedbackService(feedbackRepository);
    // arrange
    Feedback currentFeedback = feedbackRepository.findById(1L).orElseThrow();
    EmployeeId employeeId = new EmployeeId(120L);
    // act
    Feedback updatedFeedback =
        feedbackService.assignEmployeeToFeedback(currentFeedback, employeeId);

    // assert
    assertNotNull(updatedFeedback);
    assertEquals(employeeId, updatedFeedback.getEmployeeId());
  }

  @Test
  public void testAddCommentToFeedback_ShouldAddComment() {
    //    InMemoryFeedbackRepository feedbackRepository = new InMemoryFeedbackRepository();
    //    FeedbackService feedbackService = new FeedbackService(feedbackRepository);
    Comment comment = new Comment("das ist ein Kommentar von einem Mitarbeiter");
    Validation.validateComment(comment.comment());
    Feedback currentFeedback = feedbackRepository.findById(1L).orElseThrow();
    Feedback updatedFeedback = feedbackService.addCommentToFeedback(currentFeedback, comment);
    assertNotNull(updatedFeedback);
  }
}
