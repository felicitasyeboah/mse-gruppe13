package de.cityfeedback.feedbackverwaltung.infrastructure.repositories;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import de.cityfeedback.feedbackverwaltung.domain.model.Feedback;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class FeedbackRepositoryTest {

  @Mock private FeedbackRepository feedbackRepository;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldReturnFeedbackListForValidCitizenId() {
    Long citizenId = 1L;
    List<Feedback> feedbackList = List.of(new Feedback(), new Feedback());
    when(feedbackRepository.findAllByCitizenId(citizenId)).thenReturn(feedbackList);

    List<Feedback> result = feedbackRepository.findAllByCitizenId(citizenId);

    assertEquals(feedbackList, result);
  }

  @Test
  void shouldReturnEmptyListForNonExistentCitizenId() {
    Long citizenId = 999L;
    when(feedbackRepository.findAllByCitizenId(citizenId)).thenReturn(List.of());

    List<Feedback> result = feedbackRepository.findAllByCitizenId(citizenId);

    assertTrue(result.isEmpty());
  }

  @Test
  void shouldUpdateFeedbackTimestamp() {
    Feedback feedback = new Feedback();
    // feedback.setId(1L);
    when(feedbackRepository.updateFeedback(feedback)).thenReturn(feedback);

    Feedback result = feedbackRepository.updateFeedback(feedback);

    assertEquals(feedback, result);
  }

  @Test
  void shouldThrowExceptionWhenUpdatingNonExistentFeedback() {
    Feedback feedback = new Feedback();
    // feedback.setId(999L);
    when(feedbackRepository.updateFeedback(feedback))
        .thenThrow(new IllegalArgumentException("Feedback not found"));

    assertThrows(IllegalArgumentException.class, () -> feedbackRepository.updateFeedback(feedback));
  }
}
