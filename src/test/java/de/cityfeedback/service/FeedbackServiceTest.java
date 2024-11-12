package de.cityfeedback.service;

import de.cityfeedback.domain.Feedback;
import de.cityfeedback.repository.InMemoryFeedbackRepository;
import de.cityfeedback.validator.Validation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

public class FeedbackServiceTest {
    @InjectMocks
    public InMemoryFeedbackRepository feedbackRepository;
    @InjectMocks
    public FeedbackService feedbackService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        feedbackService = new FeedbackService(feedbackRepository);
    }

    @Test
    public void testCreateFeedback_ShouldCreateNewFeedback() {
        //InMemoryFeedbackRepository feedbackRepository = new InMemoryFeedbackRepository();
        //FeedbackService feedbackService = new FeedbackService(feedbackRepository);

        //arrange
        String title = "Das ist ein Testtitel";
        String content = "Das ist ein Testcontent fuer unsere testcases.";
        Long citizenId = 1L;
        Long categoryId = 2L;
        Validation.validateComplaintTitle(title);
        Validation.validateComplaintDescription(content);

        //act
        Feedback savedFeedback = feedbackService.createFeedback(title, content, citizenId, categoryId);

        //assert
        assertNotNull(savedFeedback);
        assertEquals(1L, savedFeedback.getStatusId());
        assertNotNull(savedFeedback.getCreatedAt());
        assertNotNull(savedFeedback.getUpdatedAt());
        assertNull(savedFeedback.getComment());
        assertNull(savedFeedback.getEmployeeId());
    }

    @Test
    public void testUpdateFeedbackStatus_ShouldUpdateFeedbackStatusToInBearbeitung() {
        InMemoryFeedbackRepository feedbackRepository = new InMemoryFeedbackRepository();
        FeedbackService feedbackService = new FeedbackService(feedbackRepository);

        //arrange
        Feedback currentFeedback = feedbackRepository.findById(1L);

        Long statusId = 2L; // IN BEARBEITUNG

        //act
        Feedback updatedFeedback = feedbackService.updateFeedbackStatus(currentFeedback, statusId);
        //assert
        assertNotNull(updatedFeedback);
        assertEquals(statusId, updatedFeedback.getStatusId());
    }

    @Test
    public void testAssignEmployeeToFeedback_ShouldAssignEmployee() {
        InMemoryFeedbackRepository feedbackRepository = new InMemoryFeedbackRepository();
        FeedbackService feedbackService = new FeedbackService(feedbackRepository);
        //arrange
        Feedback currentFeedback = feedbackRepository.findById(1L);
        Long employeeId = 120L;
        //act
        Feedback updatedFeedback = feedbackService.assignEmployeeToFeedback(currentFeedback, employeeId);

        //assert
        assertNotNull(updatedFeedback);
        assertEquals(employeeId, updatedFeedback.getEmployeeId());
    }

    @Test
    public void testAddCommentToFeedback_ShouldAddComment() {
        InMemoryFeedbackRepository feedbackRepository = new InMemoryFeedbackRepository();
        FeedbackService feedbackService = new FeedbackService(feedbackRepository);
        String comment = "das ist ein Kommentar von einem Mitarbeiter";
        Validation.validateComment(comment);
        Feedback currentFeedback = feedbackRepository.findById(1L);
        Feedback updatedFeedback = feedbackService.addCommentToFeedback(currentFeedback, comment);
        assertNotNull(updatedFeedback);

    }
}
