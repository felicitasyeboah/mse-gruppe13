package de.cityfeedback.feedbackverwaltung.domain.valueobject;

public enum StatusEnum {
  NEW("OFFEN"),
  IN_PROGRESS("IN BEARBEITUNG"),
  CLOSED("ABGESCHLOSSEN");

  private final String statusName;

  StatusEnum(String statusName) {
    this.statusName = statusName;
  }

  String getStatusName() {
    return this.statusName;
  }
}
