package com.exampledigisphere.vitapetcare.mail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SendMail {
  private final JavaMailSender mailSender;

  public SendMail(final JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  public void execute(String from, String to, String subject, String htmlContent) {
    log.info("SendMail");

    try {
      final var message = mailSender.createMimeMessage();
      final var helper = new MimeMessageHelper(message, true, "UTF-8");

      helper.setFrom(from);
      helper.setTo(to);
      helper.setSubject(subject);
      helper.setText(htmlContent, true);

      mailSender.send(message);
    } catch (Exception e) {
      log.error(e.getMessage());
    }
  }
}
