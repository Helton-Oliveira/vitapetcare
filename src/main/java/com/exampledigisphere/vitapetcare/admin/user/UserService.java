package com.exampledigisphere.vitapetcare.admin.user;

import com.exampledigisphere.vitapetcare.admin.file.FileService;
import com.exampledigisphere.vitapetcare.admin.user.domain.UserAssociations;
import com.exampledigisphere.vitapetcare.admin.user.repository.UserRepository;
import com.exampledigisphere.vitapetcare.admin.workDay.WorkDayService;
import com.exampledigisphere.vitapetcare.config.root.Info;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

import static com.exampledigisphere.vitapetcare.config.root.Utils.*;

@Slf4j
@Service
@Transactional
@Info(
  dev = Info.Dev.heltonOliveira,
  label = Info.Label.feature,
  date = "06/02/2026",
  description = "Serviço para gestão semântica de usuários e suas dependências"
)
public class UserService {

  private final UserRepository userRepository;
  private final WorkDayService workDayService;
  private final FileService fileService;

  public UserService(final UserRepository userRepository,
                     final WorkDayService workDayService,
                     final FileService fileService) {
    this.userRepository = userRepository;
    this.workDayService = workDayService;
    this.fileService = fileService;
  }

  @Info(
    dev = Info.Dev.heltonOliveira,
    label = Info.Label.feature,
    date = "06/02/2026",
    description = "Registra ou atualiza um usuário processando seus vínculos de forma funcional"
  )
  public Optional<UserDTO> register(UserDTO input, Set<UserAssociations> associations) {
    log.info("Matriculando/Atualizando usuário: {}", input);
    required(input, () -> "User cannot be null to save!");

    return UserFactory.createFrom(input)
      .map(userRepository::save)
      .map(usr -> usr.applyAssociations(transformToAssociationSet(associations)))
      .map(UserFactory::toResponse)
      .map(savedUser -> persistDependencies(input, savedUser));
  }

  @Info(
    dev = Info.Dev.heltonOliveira,
    label = Info.Label.feature,
    date = "06/02/2026",
    description = "Recupera um usuário específico através de seu identificador"
  )
  @Transactional(readOnly = true)
  public Optional<UserDTO> retrieve(Long id, Set<UserAssociations> associations) {
    log.info("Recuperando usuário ID: {}", id);
    required(id, () -> "User ID cannot be null to retrieve!");

    return userRepository.findById(id)
      .map(usr -> usr.applyAssociations(transformToAssociationSet(associations)))
      .map(usr -> initialize(usr, associations))
      .map(UserFactory::toResponse);
  }

  @Info(
    dev = Info.Dev.heltonOliveira,
    label = Info.Label.feature,
    date = "06/02/2026",
    description = "Lista todos os usuários ativos no sistema de forma paginada"
  )
  @Transactional(readOnly = true)
  public Page<UserDTO> list(@NonNull Pageable pageable) {
    log.info("Listando usuários paginados");
    return userRepository.findAll(pageable)
      .map(UserFactory::toResponse);
  }

  @Info(
    dev = Info.Dev.heltonOliveira,
    label = Info.Label.feature,
    date = "06/02/2026",
    description = "Suspende o acesso de um usuário ao sistema (soft delete)"
  )
  public void suspend(@NonNull Long id) {
    log.info("Suspendendo usuário ID: {}", id);
    userRepository.findById(id)
      .ifPresent(u -> {
        u.disabled();
        userRepository.save(u);
      });
  }

  private UserDTO persistDependencies(UserDTO input, UserDTO savedUser) {
    log.info("Usuario DTO antigo: {}", input);
    log.info("Usuario DTO salvo: {}", input);

    input.editedFiles().ifPresent(f -> fileService.catalog(f, savedUser));
    input.editedWorkDays().ifPresent(wk -> workDayService.assign(wk, savedUser));
    return input;
  }
}
