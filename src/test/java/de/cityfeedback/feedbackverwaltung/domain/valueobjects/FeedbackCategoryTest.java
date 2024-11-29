package de.cityfeedback.feedbackverwaltung.domain.valueobjects;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import de.cityfeedback.feedbackverwaltung.domain.valueobject.FeedbackCategory;
import org.junit.jupiter.api.Test;

class FeedbackCategoryTest {

  @Test
  void shouldReturnEnumForValidCategoryName() {
    // Test for valid display names
    assertThat(FeedbackCategory.fromCategoryName("Anfrage")).isEqualTo(FeedbackCategory.REQUEST);
    assertThat(FeedbackCategory.fromCategoryName("Beschwerde"))
        .isEqualTo(FeedbackCategory.COMPLAINT);
    assertThat(FeedbackCategory.fromCategoryName("Sonstiges")).isEqualTo(FeedbackCategory.OTHER);
  }

  @Test
  void shouldIgnoreCaseWhenMatchingCategoryName() {
    // Test for case-insensitive matching
    assertThat(FeedbackCategory.fromCategoryName("anfrage")).isEqualTo(FeedbackCategory.REQUEST);
    assertThat(FeedbackCategory.fromCategoryName("BESCHWERDE"))
        .isEqualTo(FeedbackCategory.COMPLAINT);
    assertThat(FeedbackCategory.fromCategoryName("sOnStIgEs")).isEqualTo(FeedbackCategory.OTHER);
  }

  @Test
  void shouldThrowExceptionForInvalidCategoryName() {
    // Test for invalid input
    assertThatThrownBy(() -> FeedbackCategory.fromCategoryName("Invalid"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("No enum constant with category name: Invalid");
  }

  @Test
  void shouldReturnCorrectCategoryName() {
    // Test getCategoryName method
    assertThat(FeedbackCategory.COMPLAINT.getCategoryName()).isEqualTo("Beschwerde");
    assertThat(FeedbackCategory.REQUEST.getCategoryName()).isEqualTo("Anfrage");
    assertThat(FeedbackCategory.OTHER.getCategoryName()).isEqualTo("Sonstiges");
  }
}
