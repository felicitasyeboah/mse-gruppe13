package de.cityfeedback.feedbackverwaltung.domain.events;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FeedbackCreatedEventTest {

  @Test
  void testFeedbackCreatedEventInitialization() {
    Long feedbackId = 1L;
    String category = "Beschwerde";
    String title = "Schlagloch auf der Hauptstraße";
    String content =
        "In der Hauptstraße gibt es ein großes Schlagloch, das dringend behoben werden muss.";
    Long citizenId = 101L;
    String status = "OFFEN";
    LocalDateTime createdAt = LocalDateTime.now();

    // Create an instance of FeedbackCreatedEvent
    FeedbackCreatedEvent event =
        new FeedbackCreatedEvent(
            feedbackId, category, title, content, citizenId, status, createdAt);

    // Assert that all fields are initialized correctly
    assertEquals(feedbackId, event.getFeedbackId());
    assertEquals(category, event.getCategory());
    assertEquals(title, event.getTitle());
    assertEquals(content, event.getContent());
    assertEquals(citizenId, event.getCitizenId());
    assertEquals(status, event.getStatus());
    assertEquals(createdAt, event.getCreatedAt());
  }

  @Test
  void testFeedbackCreatedEventSettersAndGetters() {
    FeedbackCreatedEvent event = new FeedbackCreatedEvent(null, null, null, null, null, null, null);

    Long feedbackId = 2L;
    String category = "Anfrage";
    String title = "Antrag auf Baumpflanzung";
    String content = "Antrag auf Baumpflanzung im Gemeindepark.";
    Long citizenId = 202L;
    String status = "OFFEN";
    LocalDateTime createdAt = LocalDateTime.now();

    // Set values
    event.setFeedbackId(feedbackId);
    event.setCategory(category);
    event.setTitle(title);
    event.setContent(content);
    event.setCitizenId(citizenId);
    event.setStatus(status);
    event.setCreatedAt(createdAt);

    // Assert that getters return the correct values
    assertEquals(feedbackId, event.getFeedbackId());
    assertEquals(category, event.getCategory());
    assertEquals(title, event.getTitle());
    assertEquals(content, event.getContent());
    assertEquals(citizenId, event.getCitizenId());
    assertEquals(status, event.getStatus());
    assertEquals(createdAt, event.getCreatedAt());
  }

  @Test
  void testFeedbackCreatedEventToString() {
    Long feedbackId = 3L;
    String category = "Sonstiges";
    String title = "Klinik Öffnungszeiten";
    String content = "Bitte verlängern Sie die Öffnungszeiten der Klinik an den Wochenenden.";
    Long citizenId = 303L;
    String status = "OFFEN";
    LocalDateTime createdAt = LocalDateTime.now();

    FeedbackCreatedEvent event =
        new FeedbackCreatedEvent(
            feedbackId, category, title, content, citizenId, status, createdAt);

    String expectedString =
        "FeedbackCreatedEvent(feedbackId=3, category=Sonstiges, title=Klinik Öffnungszeiten, content=Bitte verlängern Sie die Öffnungszeiten der Klinik an den Wochenenden., citizenId=303, status=OFFEN, createdAt="
            + createdAt
            + ")";

    assertEquals(expectedString, event.toString());
  }
}
