package de.cityfeedback.service;

import de.cityfeedback.domain.Feedback;
import de.cityfeedback.repository.InMemoryFeedbackRepository;
import de.cityfeedback.validator.Validation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class FeedbackServiceTest {
    private FeedbackService feedbackService;
    private InMemoryFeedbackRepository feedbackRepository;

    @BeforeEach
    void setUp() {
        feedbackRepository = new InMemoryFeedbackRepository();
        feedbackService = new FeedbackService(feedbackRepository);
    }

    @Test
    void testCreateFeedback_ShouldCreateNewFeedback() {
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
    void testUpdateFeedbackStatus_ShouldUpdateFeedbackStatusToInBearbeitung() {
        //arrange
        Feedback feedback = feedbackRepository.findById(1L);

        Long statusId = 2L; // IN BEARBEITUNG
        feedback.setUpdatedAt(new Date().toInstant());
        //act
        Feedback updatedFeedback = feedbackService.updateFeedbackStatus(feedback, statusId);
        //assert
        assertNotNull(updatedFeedback);
        assertEquals(statusId, updatedFeedback.getStatusId());
    }

    @Test
    void testAssignEmployeeToFeedback_ShouldAssignEmployee() {
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
}
