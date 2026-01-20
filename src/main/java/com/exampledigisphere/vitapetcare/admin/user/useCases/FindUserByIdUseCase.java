package com.exampledigisphere.vitapetcare.admin.user.useCases;

import com.exampledigisphere.vitapetcare.admin.user.domain.User;
import com.exampledigisphere.vitapetcare.admin.user.repository.UserRepository;
import com.exampledigisphere.vitapetcare.config.root.Info;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
@Info(
  dev = Info.Dev.heltonOliveira,
  label = Info.Label.feature,
  date = "19/01/2026",
  description = "UseCase para buscar um usuário por ID"
)
public class FindUserByIdUseCase {

  private final UserRepository userRepository;

  @Info(
    dev = Info.Dev.heltonOliveira,
    label = Info.Label.feature,
    date = "19/01/2026",
    description = "Busca usuário por ID com associações"
  )
  @Transactional(readOnly = true)
  public Optional<User> execute(@NonNull Long id) {
    log.info("Buscando usuário por ID: {}", id);

    return userRepository.findById(id);
  }
}
