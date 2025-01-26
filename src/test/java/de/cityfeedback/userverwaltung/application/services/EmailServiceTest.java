package de.cityfeedback.userverwaltung.application.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

  @Mock private JavaMailSender mailSender;
  @Mock private MimeMessage mimeMessage;
  @Mock private MimeMessageHelper mimeMessageHelper;

  @InjectMocks private EmailService emailService;

  @BeforeEach
  void setUp() throws MessagingException {
    when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
    new MimeMessageHelper(mimeMessage, true); // Initialize MimeMessageHelper
  }

  @Test
  void sendFeedbackUpdatedEmail_ShouldSendEmail() throws MessagingException {
    String to = "john.doe@example.com";
    String subject = "Feedback Updated";
    String text = "Your feedback has been updated.";

    emailService.sendFeedbackUpdatedEmail(to, subject, text);

    verify(mailSender, times(1)).send(mimeMessage);
  }

  @Test
  void sendFeedbackUpdatedEmail_ShouldThrowMessagingException() throws MessagingException {
    String to = "john.doe@example.com";
    String subject = "Feedback Updated";
    String text = "Your feedback has been updated.";

    doThrow(new RuntimeException("Email service unavailable")).when(mailSender).send(mimeMessage);

    try {
      emailService.sendFeedbackUpdatedEmail(to, subject, text);
    } catch (RuntimeException | MessagingException e) {
      assertEquals("Email service unavailable", e.getMessage());
    }

    verify(mailSender, times(1)).send(mimeMessage);
  }
}
