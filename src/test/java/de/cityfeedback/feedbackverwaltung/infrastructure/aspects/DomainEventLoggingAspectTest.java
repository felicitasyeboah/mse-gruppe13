package de.cityfeedback.feedbackverwaltung.infrastructure.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import static org.mockito.Mockito.*;

@Aspect
class DomainEventLoggingAspectTest {

  private DomainEventLoggingAspect aspect;
  private Logger logger;
  private JoinPoint joinPoint;

  @BeforeEach
  void setUp() {
    logger = mock(Logger.class);
    joinPoint = mock(JoinPoint.class);
    Signature signature = mock(Signature.class);
    aspect = new DomainEventLoggingAspect();
    DomainEventLoggingAspect.logger = logger;
    when(joinPoint.getSignature()).thenReturn(signature);
  }

  @Test
  void logBeforeShouldLogMethodNameAndArguments() {
    when(joinPoint.getSignature().getName()).thenReturn("methodName");
    when(joinPoint.getArgs()).thenReturn(new Object[] {"arg1", "arg2"});

    aspect.logBefore(joinPoint);

    verify(logger).info("Methode aufgerufen: methodName");
    verify(logger).info("Argumente: [arg1, arg2]");
  }

  @Test
  void logAfterReturningShouldLogMethodNameAndResult() {
    when(joinPoint.getSignature().getName()).thenReturn("methodName");

    aspect.logAfterReturning(joinPoint, "result");

    verify(logger).info("Methode zurückgegeben: methodName");
    verify(logger).info("Ergebnis: result");
  }

  @Test
  void logAfterThrowingShouldLogMethodNameAndException() {
    when(joinPoint.getSignature().getName()).thenReturn("methodName");
    Throwable exception = new RuntimeException("error");

    aspect.logAfterThrowing(joinPoint, exception);

    verify(logger).error("Fehler in Methode: methodName", exception);
  }
}
