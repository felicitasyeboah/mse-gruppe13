package de.cityfeedback.feedbackverwaltung.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import java.util.logging.Logger;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = Logger.getLogger(LoggingAspect.class.getName());

    @Before("execution(* de.cityfeedback.feedbackverwaltung.domain.listener.FeedbackCreatedEventListener.handleFeedbackCreatedEvent(..))")
    public void logBeforeMethodExecution(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (args.length > 0 && args[0] instanceof de.cityfeedback.feedbackverwaltung.domain.events.FeedbackCreatedEvent event) {
            logger.info("LoggingAspect: Handling event with ID: " + event.getFeedbackId() + ", Title: " + event.getTitle());
        } else {
            logger.info("LoggingAspect: Method called: " + joinPoint.getSignature());
        }
    }
}