package com.exampledigisphere.vitapetcare.admin.file.useCases;

import com.exampledigisphere.vitapetcare.admin.file.domain.File;
import com.exampledigisphere.vitapetcare.admin.file.repository.FileRepository;
import com.exampledigisphere.vitapetcare.config.root.Info;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
@Info(
  dev = Info.Dev.heltonOliveira,
  label = Info.Label.feature,
  date = "19/01/2026",
  description = "UseCase para salvar ou atualizar um arquivo"
)
public class SaveFileUseCase {

  private final FileRepository fileRepository;

  @Info(
    dev = Info.Dev.heltonOliveira,
    label = Info.Label.feature,
    date = "19/01/2026",
    description = "Salva um arquivo e retorna sua visualização"
  )
  public Optional<File> execute(@NonNull File input) {
    log.info("Iniciando persistência do arquivo: {}", input);

    return Optional.of(input)
      .map(fileRepository::save);
  }
}
