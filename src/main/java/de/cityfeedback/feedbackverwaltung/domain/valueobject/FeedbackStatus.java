package de.cityfeedback.feedbackverwaltung.domain.valueobject;

import lombok.Getter;

@Getter
public enum FeedbackStatus {
  NEW("OFFEN"),
  IN_PROGRESS("IN BEARBEITUNG"),
  CLOSED("ABGESCHLOSSEN");

  private final String statusName;

  FeedbackStatus(String statusName) {
    this.statusName = statusName;
  }

  // Static method to find by display name
  public static FeedbackStatus fromStatusName(String statusName) {
    for (FeedbackStatus status : FeedbackStatus.values()) {
      if (status.statusName.equalsIgnoreCase(statusName)) {
        return status;
      }
    }
    throw new IllegalArgumentException(
        "Invalid Status! No enum constant with status name: " + statusName);
  }
}
