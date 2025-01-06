package de.cityfeedback.feedbackverwaltung.domain.valueobject;

import lombok.Getter;

@Getter
public enum FeedbackCategory {
  COMPLAINT("Beschwerde"),
  REQUEST("Anfrage"),
  OTHER("Sonstiges");

  private final String categoryName;

  FeedbackCategory(String categoryName) {
    this.categoryName = categoryName;
  }

  // Static method to find by display name
  public static FeedbackCategory fromCategoryName(String categoryName) {
    for (FeedbackCategory category : FeedbackCategory.values()) {
      if (category.categoryName.equalsIgnoreCase(categoryName)) {
        return category;
      }
    }
    throw new IllegalArgumentException(
        "Invalid Category! No enum constant with category name: " + categoryName);
  }
}
