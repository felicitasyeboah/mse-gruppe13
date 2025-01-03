package de.cityfeedback.userverwaltung.application.listeners;

import de.cityfeedback.userverwaltung.domain.events.UserLoggedInEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class UserEventListener {

    @EventListener
    public void handleUserLoggedInEvent(UserLoggedInEvent event) {
        System.out.println("Benutzer hat sich erfolgreich eingeloggt: " + event.getEmail());
    }
}