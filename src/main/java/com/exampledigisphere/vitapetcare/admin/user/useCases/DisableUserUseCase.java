package com.exampledigisphere.vitapetcare.admin.user.useCases;

import com.exampledigisphere.vitapetcare.admin.user.repository.UserRepository;
import com.exampledigisphere.vitapetcare.config.root.Info;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
@Info(
  dev = Info.Dev.heltonOliveira,
  label = Info.Label.feature,
  date = "19/01/2026",
  description = "UseCase para desativar um usuário"
)
public class DisableUserUseCase {

  private final UserRepository userRepository;

  @Info(
    dev = Info.Dev.heltonOliveira,
    label = Info.Label.feature,
    date = "19/01/2026",
    description = "Desativa um usuário (soft delete)"
  )
  public void execute(@NonNull Long id) {
    log.info("Desativando usuário ID: {}", id);

    userRepository.findById(id)
      .ifPresent(u -> {
        u.disabled();
        userRepository.save(u);
      });
  }
}
