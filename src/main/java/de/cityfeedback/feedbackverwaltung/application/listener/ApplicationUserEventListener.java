package de.cityfeedback.feedbackverwaltung.application.listener;

import de.cityfeedback.feedbackverwaltung.application.dto.UserDto;
import de.cityfeedback.feedbackverwaltung.application.services.UserCacheService;
import de.cityfeedback.shared.events.UserRegisteredEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationUserEventListener {
  private final UserCacheService userCacheService;

  public ApplicationUserEventListener(UserCacheService userCacheService) {
    this.userCacheService = userCacheService;
  }

  @EventListener
  public String handleUserRegisteredEvent(UserRegisteredEvent event) {
    UserDto user =
        new UserDto(event.getUserId(), event.getUsername(), event.getEmail(), event.getRole());
    userCacheService.cacheUser(user);
    return "Benutzerdaten wurden aktualisiert: EventId "
        + event.getEventId()
        + " - Event occured on:"
        + event.getEventOccurredOn()
        + " - userName: "
        + user.getUserName()
        + " - userEmail: "
        + user.getEmail()
        + " - userRole: "
        + user.getRole()
        + " - userId:"
        + user.getUserId();
  }
}
