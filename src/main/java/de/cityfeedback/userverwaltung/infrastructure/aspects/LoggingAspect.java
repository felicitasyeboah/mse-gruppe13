package de.cityfeedback.userverwaltung.infrastructure.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Pointcut("execution(* de.cityfeedback.userverwaltung.application.services.UserService.*(..))")
    public void userServiceMethods() {}

    // Vor jeder Methode in UserService wird dieser Code ausgeführt
    @Before("userServiceMethods()")
    public void logMethodStart() {
        System.out.println("Method is starting...");
    }

    // Nach jeder Methode in UserService wird dieser Code ausgeführt
    @After("userServiceMethods()")
    public void logMethodEnd() {
        System.out.println("Method has finished executing.");
    }
}