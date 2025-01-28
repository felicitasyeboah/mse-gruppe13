package de.cityfeedback.feedbackverwaltung.infrastructure.aspects;

import java.util.Arrays;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DomainEventLoggingAspect {

  static Logger logger = LoggerFactory.getLogger(DomainEventLoggingAspect.class);

  @Pointcut("execution(* de.cityfeedback.feedbackverwaltung.domain.listener.*.*(..))")
  public void domainEventHandler() {}

  @Before("domainEventHandler()")
  public void logBefore(JoinPoint joinPoint) {
    logger.info("Methode aufgerufen: " + joinPoint.getSignature().getName());
    logger.info("Argumente: " + Arrays.toString(joinPoint.getArgs()));
  }

  @AfterReturning(value = "domainEventHandler()", returning = "result")
  public void logAfterReturning(JoinPoint joinPoint, Object result) {
    logger.info("Methode zurückgegeben: " + joinPoint.getSignature().getName());
    logger.info("Ergebnis: " + result);
  }

  @AfterThrowing(value = "domainEventHandler()", throwing = "exception")
  public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
    logger.error("Fehler in Methode: " + joinPoint.getSignature().getName(), exception);
  }
}
