package com.exampledigisphere.vitapetcare.admin.file.useCases;

import com.exampledigisphere.vitapetcare.admin.file.domain.File;
import com.exampledigisphere.vitapetcare.admin.user.domain.User;
import com.exampledigisphere.vitapetcare.config.root.Info;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
@Info(
  dev = Info.Dev.heltonOliveira,
  label = Info.Label.feature,
  date = "19/01/2026",
  description = "UseCase para salvar múltiplos arquivos vinculados a um usuário"
)
public class SaveAllFilesUseCase {

  private final SaveFileUseCase saveFileUseCase;

  @Info(
    dev = Info.Dev.heltonOliveira,
    label = Info.Label.feature,
    date = "19/01/2026",
    description = "Salva todos os arquivos vinculados a um usuário"
  )
  public void execute(@NonNull Set<File> files, @NonNull User user) {
    log.info("SaveAllFilesUseCase: {}, {}", files, user);

    files.stream()
      .peek(file -> file.setUser(user))
      .forEach(saveFileUseCase::execute);
  }
}
