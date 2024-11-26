package de.cityfeedback.feedbackverwaltung.domain.valueobject;

public enum CategoryEnum {
  COMPLAINT("Beschwerde"),
  REQUEST("Anfrage"),
  OTHER("Sonstiges");

  private final String categoryName;

  CategoryEnum(String categoryName) {
    this.categoryName = categoryName;
  }

  String getCategoryName() {
    return this.categoryName;
  }
}
