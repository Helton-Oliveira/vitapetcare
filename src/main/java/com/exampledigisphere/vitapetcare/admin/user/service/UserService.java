package com.exampledigisphere.vitapetcare.admin.user.service;

import com.exampledigisphere.vitapetcare.admin.file.domain.File;
import com.exampledigisphere.vitapetcare.admin.file.service.FileService;
import com.exampledigisphere.vitapetcare.admin.user.domain.User;
import com.exampledigisphere.vitapetcare.admin.user.domain.UserInput;
import com.exampledigisphere.vitapetcare.admin.user.domain.UserMapper;
import com.exampledigisphere.vitapetcare.admin.user.domain.UserOutput;
import com.exampledigisphere.vitapetcare.admin.user.repository.UserRepository;
import com.exampledigisphere.vitapetcare.config.root.Info;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.exampledigisphere.vitapetcare.config.root.OptionalUtils.peek;

@Service
@Transactional
@Slf4j
@Info(
  dev = Info.Dev.heltonOliveira,
  label = Info.Label.feature,
  date = "29/12/2025",
  description = "Serviço para gestão de usuários com lógica funcional"
)
public class UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final PasswordEncoder passwordEncoder;
  private final FileService fileService;

  public UserService(final UserRepository userRepository,
                     final UserMapper userMapper,
                     final PasswordEncoder passwordEncoder,
                     final FileService fileService) {
    this.userRepository = userRepository;
    this.userMapper = userMapper;
    this.passwordEncoder = passwordEncoder;
    this.fileService = fileService;
  }

  @Info(
    dev = Info.Dev.heltonOliveira,
    label = Info.Label.feature,
    date = "29/12/2025",
    description = "Salva um novo usuário com senha codificada"
  )
  public Optional<UserOutput> save(@NonNull UserInput input) {
    log.info("Iniciando criação de usuário para o email: {}", input.email());

    final var editedFiles = Optional.ofNullable(input.files())
      .orElse(new HashSet<>())
      .stream()
      .filter(File::wasEdited)
      .collect(Collectors.toSet());

    return Optional.of(input)
      .map(userMapper::toDomain)
      .map(peek(user -> {
        user.setPassword(passwordEncoder.encode(input.password()));
        user.setFiles(new HashSet());
      }))
      .map(userRepository::save)
      .map(peek(usr -> fileService.saveAllAndFlush(editedFiles, usr)))
      .map(userMapper::toOutput);
  }

  @Info(
    dev = Info.Dev.heltonOliveira,
    label = Info.Label.feature,
    date = "29/12/2025",
    description = "Atualiza dados de um usuário existente"
  )
  public Optional<UserOutput> update(@NonNull UserInput input) {
    log.info("Atualizando usuário ID: {}", input.id());

    return userRepository.findById(input.id())
      .map(user -> {
        user.setName(input.name());
        user.setEmail(input.email());
        if (input.password() != null && !input.password().isBlank()) {
          user.setPassword(passwordEncoder.encode(input.password()));
        }
        return user;
      })
      .map(userRepository::save)
      .map(userMapper::toOutput);
  }

  @Info(
    dev = Info.Dev.heltonOliveira,
    label = Info.Label.feature,
    date = "29/12/2025",
    description = "Busca todos os usuários ativos"
  )
  public List<UserOutput> findAll() {
    log.info("Buscando todos os usuários");

    return userRepository.findAll().stream()
      .map(userMapper::toOutput)
      .collect(Collectors.toList());
  }

  @Info(
    dev = Info.Dev.heltonOliveira,
    label = Info.Label.feature,
    date = "29/12/2025",
    description = "Busca usuário por ID"
  )
  public Optional<UserOutput> findById(@NonNull Long id) {
    log.info("Buscando usuário por ID: {}", id);

    return userRepository.findById(id)
      .map(userMapper::toOutput);
  }

  @Info(
    dev = Info.Dev.heltonOliveira,
    label = Info.Label.feature,
    date = "29/12/2025",
    description = "Desativa um usuário (soft delete)"
  )
  public void disable(@NonNull Long id) {
    log.info("Desativando usuário ID: {}", id);

    userRepository.findById(id)
      .map(peek(User::disabled))
      .map(userRepository::save);
  }
}
