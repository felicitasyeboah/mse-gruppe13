package de.cityfeedback.domain.valueobjects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import de.cityfeedback.feedbackverwaltung.domain.valueobject.FeedbackStatus;
import org.junit.jupiter.api.Test;

class FeedbackStatusTest {

  @Test
  void shouldReturnEnumForValidStatusName() {
    // Test for valid display names
    assertThat(FeedbackStatus.fromStatusName("OFFEN")).isEqualTo(FeedbackStatus.NEW);
    assertThat(FeedbackStatus.fromStatusName("IN BEARBEITUNG"))
        .isEqualTo(FeedbackStatus.IN_PROGRESS);
    assertThat(FeedbackStatus.fromStatusName("ABGESCHLOSSEN")).isEqualTo(FeedbackStatus.CLOSED);
  }

  @Test
  void shouldIgnoreCaseWhenMatchingStatusName() {
    // Test for case-insensitive matching
    assertThat(FeedbackStatus.fromStatusName("offen")).isEqualTo(FeedbackStatus.NEW);
    assertThat(FeedbackStatus.fromStatusName("in bearbeitung"))
        .isEqualTo(FeedbackStatus.IN_PROGRESS);
    assertThat(FeedbackStatus.fromStatusName("abgeschlossen")).isEqualTo(FeedbackStatus.CLOSED);
  }

  @Test
  void shouldThrowExceptionForInvalidStatusName() {
    // Test for invalid input
    assertThatThrownBy(() -> FeedbackStatus.fromStatusName("INVALID"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("No enum constant with status name: INVALID");
  }

  @Test
  void shouldReturnCorrectStatusName() {
    // Test getStatusName method
    assertThat(FeedbackStatus.NEW.getStatusName()).isEqualTo("OFFEN");
    assertThat(FeedbackStatus.IN_PROGRESS.getStatusName()).isEqualTo("IN BEARBEITUNG");
    assertThat(FeedbackStatus.CLOSED.getStatusName()).isEqualTo("ABGESCHLOSSEN");
  }
}
