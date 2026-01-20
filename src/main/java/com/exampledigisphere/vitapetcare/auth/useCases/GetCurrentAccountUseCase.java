package com.exampledigisphere.vitapetcare.auth.useCases;

import com.exampledigisphere.vitapetcare.admin.user.domain.User;
import com.exampledigisphere.vitapetcare.admin.user.repository.UserRepository;
import com.exampledigisphere.vitapetcare.config.root.Info;
import com.exampledigisphere.vitapetcare.config.root.util.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@Info(
  dev = Info.Dev.heltonOliveira,
  label = Info.Label.feature,
  date = "19/01/2026",
  description = "UseCase para obter os dados da conta autenticada"
)
public class GetCurrentAccountUseCase {

  private final UserRepository userRepository;

  public GetCurrentAccountUseCase(final UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Transactional(readOnly = true)
  @Info(
    dev = Info.Dev.heltonOliveira,
    label = Info.Label.feature,
    date = "19/01/2026",
    description = "Busca o usuário autenticado no banco de dados com associações"
  )
  public Optional<User> execute() {
    String username = SecurityUtils.getAuthenticatedUsername();
    log.info("Buscando conta atual para o usuário: {}", username);
    return userRepository.findByUsername(username);
  }
}
