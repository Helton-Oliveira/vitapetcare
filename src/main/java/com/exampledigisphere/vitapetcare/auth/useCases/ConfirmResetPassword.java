package com.exampledigisphere.vitapetcare.auth.useCases;

import com.exampledigisphere.vitapetcare.admin.user.repository.UserRepository;
import com.exampledigisphere.vitapetcare.auth.DTO.ConfirmPasswordReset;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class ConfirmResetPassword {
  private final UserRepository userRepository;

  public ConfirmResetPassword(final UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public boolean execute(@NonNull ConfirmPasswordReset input) {
    log.info("ConfirmResetPassword: {}", input);

    return userRepository.findByResetCode(input.resetCode())
      .map(
        (user) -> {
          if (user.verifyResetCode(input.resetCode())) {
            user.setPassword(input.newPassword())
              .encryptPassword();
            user.markResetCodeUsed();
            return true;
          }
          return false;
        })
      .orElse(false);
  }
}
