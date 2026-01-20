package com.exampledigisphere.vitapetcare.admin.user.useCases;

import com.exampledigisphere.vitapetcare.admin.file.domain.File;
import com.exampledigisphere.vitapetcare.admin.file.useCases.SaveAllFilesUseCase;
import com.exampledigisphere.vitapetcare.admin.user.domain.User;
import com.exampledigisphere.vitapetcare.admin.user.repository.UserRepository;
import com.exampledigisphere.vitapetcare.admin.workDay.domain.WorkDay;
import com.exampledigisphere.vitapetcare.admin.workDay.useCases.SaveAllWorkDaysUseCase;
import com.exampledigisphere.vitapetcare.config.root.Info;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
@Info(
  dev = Info.Dev.heltonOliveira,
  label = Info.Label.feature,
  date = "19/01/2026",
  description = "UseCase para salvar ou atualizar um usuário"
)
public class SaveUserUseCase {

  private final UserRepository userRepository;
  private final SaveAllWorkDaysUseCase saveAllWorkDaysUseCase;
  private final SaveAllFilesUseCase saveAllFilesUseCase;

  @Info(
    dev = Info.Dev.heltonOliveira,
    label = Info.Label.feature,
    date = "19/01/2026",
    description = "Salva um usuário e processa seus vínculos (arquivos e dias de trabalho)"
  )
  public Optional<User> execute(@NonNull User input) {
    log.info("SaveUserUseCase: {}", input);

    final var editedWorkDays = input.getWorkDays().stream()
      .filter(Objects::nonNull)
      .filter(WorkDay::isEdited)
      .collect(Collectors.toSet());

    final var editedFiles = input.getFiles().stream()
      .filter(Objects::nonNull)
      .filter(File::isEdited)
      .collect(Collectors.toSet());

    return Optional.of(input).stream()
      .peek(User::encryptPassword)
      .peek(User::prepareForCreation)
      .map(userRepository::save)
      .peek(usr -> saveAllFilesUseCase.execute(editedFiles, usr))
      .peek(usr -> saveAllWorkDaysUseCase.execute(editedWorkDays, usr))
      .findFirst();
  }
}
