package de.cityfeedback.service;

import de.cityfeedback.domain.Feedback;
import de.cityfeedback.repository.InMemoryFeedbackRepository;
import de.cityfeedback.validator.Validation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.Date;

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

        System.out.println("result: " + savedFeedback);

    }

    @Test
    public void testUpdateFeedbackStatus_ShouldUpdateFeedbackStatusToInBearbeitung() {
        //arrange
        Feedback feedback = feedbackRepository.findById(1L);

        Long statusId = 2L; // IN BEARBEITUNG
        Instant updateTime = new Date().toInstant();
        feedback.setUpdatedAt(updateTime);
        //act
        Feedback updatedFeedback = feedbackService.updateFeedbackStatus(feedback, statusId);
        //assert
        assertEquals(updatedFeedback.getUpdatedAt(), updateTime);
        assertNotNull(updatedFeedback);
        assertEquals(statusId, updatedFeedback.getStatusId());
    }

    @Test
    public void testAssignEmployeeToFeedback_ShouldAssignEmployee() {
        //arrange
        Feedback feedback = feedbackRepository.findById(1L);
        Long employeeId = 120L;
        feedback.setUpdatedAt(new Date().toInstant());
        //act
        Feedback updatedFeedback = feedbackService.assignEmployeeToFeedback(feedback, employeeId);

        //assert
        assertNotNull(updatedFeedback);
        assertEquals(employeeId, updatedFeedback.getEmployeeId());
    }

    @Test
    public void testAddCommentToFeedback_ShouldAddComment() {
        String comment = "das ist ein Kommentar von einem Mitarbeiter";
        Validation.validateComment(comment);
        Feedback feedback = feedbackRepository.findById(1L);
        Instant updateTime = new Date().toInstant();
        feedback.setUpdatedAt(updateTime);
        Feedback updatedFeedback = feedbackService.addCommentToFeedback(feedback, comment);
        assertNotNull(feedback);
        assertEquals(updateTime, updatedFeedback.getUpdatedAt());

    }
}
