package com.exampledigisphere.vitapetcare.auth.useCases;

import com.exampledigisphere.vitapetcare.admin.user.domain.User;
import com.exampledigisphere.vitapetcare.admin.user.repository.UserRepository;
import com.exampledigisphere.vitapetcare.auth.DTO.ResetPasswordRequest;
import com.exampledigisphere.vitapetcare.mail.SendMail;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@Slf4j
@Transactional
@AllArgsConstructor
public class RecoveryPasswordRequest {
  private final UserRepository userRepository;
  private final SendMail sendMail;
  private final TemplateEngine templateEngine;

  public boolean execute(@NonNull ResetPasswordRequest input) {
    return userRepository.findByUsername(input.email())
      .map(user -> {
        try {
          processPasswordRecovery(user, input.url());
          return true;
        } catch (Exception e) {
          log.error("Erro ao processar recuperação de senha para {}: {}", user.getEmail(), e.getMessage());
          return false;
        }
      })
      .orElse(false);
  }

  private void processPasswordRecovery(User user, String baseUrl) {
    user.generateResetCode();

    final var resetLink = String.format("%s?resetCode=%s", baseUrl, user.getResetCode());
    final var context = new Context();
    context.setVariable("user", user);
    context.setVariable("resetLink", resetLink);

    sendMail.execute(
      "contato@vitapetcare.com",
      user.getEmail(),
      "Recuperação de Senha",
      templateEngine.process("password-reset", context)
    );

    userRepository.save(user);
  }
}
