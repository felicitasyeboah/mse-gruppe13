package de.cityfeedback.userverwaltung.domain.listener;

import de.cityfeedback.userverwaltung.domain.events.UserLoggedInEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DomainUserEventListener {

  @EventListener
  public String handleUserLoggedInEvent(UserLoggedInEvent event) {
    return "Authenticated user with email: " + event.getEmail();
  }
}
