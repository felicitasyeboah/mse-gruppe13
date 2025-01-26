package de.cityfeedback.userverwaltung.application.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
  private final JavaMailSender mailSender;

  public EmailService(JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  public void sendFeedbackUpdatedEmail(String to, String subject, String text)
      throws MessagingException {
    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message, true);

    helper.setFrom("response_email_mse_gruppe12@web.de");
    helper.setTo(to);
    helper.setSubject(subject);
    helper.setText(text, true);
    helper.setSentDate(new java.util.Date());

    mailSender.send(message);
  }
}
