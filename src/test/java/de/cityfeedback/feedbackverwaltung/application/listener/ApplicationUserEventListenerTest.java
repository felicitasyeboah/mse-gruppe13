package de.cityfeedback.feedbackverwaltung.application.listener;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import de.cityfeedback.feedbackverwaltung.application.dto.UserDto;
import de.cityfeedback.feedbackverwaltung.application.services.UserCacheService;
import de.cityfeedback.shared.events.UserRegisteredEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ApplicationUserEventListenerTest {
  @Mock private UserCacheService userCacheService;

  @InjectMocks private ApplicationUserEventListener applicationUserEventListener;

  private UserRegisteredEvent userRegisteredEvent;

  @BeforeEach
  void setUp() {
    userRegisteredEvent =
        new UserRegisteredEvent(1L, "John Doe", "john.doe@example.com", "CITIZEN");
  }

  @Test
  void handleUserRegisteredEvent_ShouldCacheUserAndReturnExpectedMessage() {
    ArgumentCaptor<UserDto> userCaptor = ArgumentCaptor.forClass(UserDto.class);

    String result = applicationUserEventListener.handleUserRegisteredEvent(userRegisteredEvent);

    verify(userCacheService, times(1)).cacheUser(userCaptor.capture());
    UserDto capturedUser = userCaptor.getValue();

    assertEquals(1L, capturedUser.getUserId());
    assertEquals("John Doe", capturedUser.getUserName());
    assertEquals("john.doe@example.com", capturedUser.getEmail());
    assertEquals("CITIZEN", capturedUser.getRole());

    assertEquals(
        "Benutzerdaten wurden aktualisiert: EventId "
            + userRegisteredEvent.getEventId()
            + " - Event occured on:"
            + userRegisteredEvent.getEventOccurredOn()
            + " - userName: John Doe - userEmail: john.doe@example.com - userRole: CITIZEN - userId:1",
        result);
  }
}
