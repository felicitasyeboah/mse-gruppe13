package de.cityfeedback.userverwaltung.infrastructure.aspects;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

  private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

  @Pointcut(
      "execution(* de.cityfeedback.userverwaltung.application.services.UserService.authenticateUser(..))")
  public void userLoginMethod() {}

  @Before("userLoginMethod()")
  public void logLoginStart() {
    logger.info("User login process is starting...");
  }

  @After("userLoginMethod()")
  public void logLoginEnd() {
    logger.info("User login process has finished executing.");
  }
}
