package de.cityfeedback.userverwaltung.infrastructure.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.AfterThrowing;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExceptionHandlingAspect {

    @AfterThrowing(pointcut = "execution(* de.cityfeedback.userverwaltung.application.services.UserService.*(..))", throwing = "exception")
    public void handleException(Exception exception) {
        System.out.println("An error occurred: " + exception.getMessage());
    }
}