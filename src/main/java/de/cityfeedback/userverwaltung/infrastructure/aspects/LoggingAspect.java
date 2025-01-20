package de.cityfeedback.userverwaltung.infrastructure.aspects;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

  @Pointcut("execution(* de.cityfeedback.userverwaltung.application.services.UserService.*(..))")
  public void userServiceMethods() {}

  @Before("userServiceMethods()")
  public void logMethodStart() {
    System.out.println("Userlogin is starting...");
  }

  @After("userServiceMethods()")
  public void logMethodEnd() {
    System.out.println("Userlogin has finished executing.");
  }
}
