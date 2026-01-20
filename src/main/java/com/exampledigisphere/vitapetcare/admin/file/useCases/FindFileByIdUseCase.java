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
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
@Info(
  dev = Info.Dev.heltonOliveira,
  label = Info.Label.feature,
  date = "19/01/2026",
  description = "UseCase para buscar um arquivo por ID"
)
public class FindFileByIdUseCase {

  private final FileRepository fileRepository;

  @Info(
    dev = Info.Dev.heltonOliveira,
    label = Info.Label.feature,
    date = "19/01/2026",
    description = "Busca arquivo por ID com associações"
  )
  public Optional<File> execute(@NonNull Long id) {
    log.info("Buscando arquivo por ID: {}", id);

    return fileRepository.findById(id);
  }
}
