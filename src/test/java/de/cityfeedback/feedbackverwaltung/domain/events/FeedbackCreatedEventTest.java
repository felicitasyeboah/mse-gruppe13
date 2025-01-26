package de.cityfeedback.feedbackverwaltung.domain.events;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class FeedbackCreatedEventTest {

  @Test
  void feedbackCreatedEventInitialization() {
    Long feedbackId = 1L;
    String category = "Complaint";
    String title = "Pothole on Main Street";
    String content = "There is a large pothole on Main Street that needs urgent attention.";
    Long citizenId = 101L;
    String status = "OPEN";
    LocalDateTime createdAt = LocalDateTime.now();

    FeedbackCreatedEvent event =
        new FeedbackCreatedEvent(
            feedbackId, category, title, content, citizenId, status, createdAt);

    assertEquals(feedbackId, event.getFeedbackId());
    assertEquals(category, event.getCategory());
    assertEquals(title, event.getTitle());
    assertEquals(content, event.getContent());
    assertEquals(citizenId, event.getCitizenId());
    assertEquals(status, event.getStatus());
    assertEquals(createdAt, event.getCreatedAt());
  }

  @Test
  void feedbackCreatedEventSettersAndGetters() {

    Long feedbackId = 2L;
    String category = "Request";
    String title = "Tree Planting Request";
    String content = "Request to plant trees in the community park.";
    Long citizenId = 202L;
    String status = "OPEN";
    LocalDateTime createdAt = LocalDateTime.now();

    FeedbackCreatedEvent event =
        new FeedbackCreatedEvent(
            feedbackId, category, title, content, citizenId, status, createdAt);

    assertEquals(feedbackId, event.getFeedbackId());
    assertEquals(category, event.getCategory());
    assertEquals(title, event.getTitle());
    assertEquals(content, event.getContent());
    assertEquals(citizenId, event.getCitizenId());
    assertEquals(status, event.getStatus());
    assertEquals(createdAt, event.getCreatedAt());
  }

  @Test
  void feedbackCreatedEventToString() {
    Long feedbackId = 3L;
    String category = "Other";
    String title = "Clinic Hours";
    String content = "Please extend the clinic hours on weekends.";
    Long citizenId = 303L;
    String status = "OPEN";
    LocalDateTime createdAt = LocalDateTime.now();

    FeedbackCreatedEvent event =
        new FeedbackCreatedEvent(
            feedbackId, category, title, content, citizenId, status, createdAt);

    String expectedString =
        "FeedbackCreatedEvent(feedbackId=3, category=Other, title=Clinic Hours, content=Please extend the clinic hours on weekends., citizenId=303, status=OPEN, createdAt="
            + createdAt
            + ")";

    assertEquals(expectedString, event.toString());
  }
}
