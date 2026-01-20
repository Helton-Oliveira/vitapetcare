package com.exampledigisphere.vitapetcare.admin.file.useCases;

import com.exampledigisphere.vitapetcare.admin.file.repository.FileRepository;
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
  description = "UseCase para desativar um arquivo"
)
public class DisableFileUseCase {

  private final FileRepository fileRepository;

  @Info(
    dev = Info.Dev.heltonOliveira,
    label = Info.Label.feature,
    date = "19/01/2026",
    description = "Desativa um arquivo (soft delete)"
  )
  public void execute(@NonNull Long id) {
    log.info("Desativando arquivo ID: {}", id);

    fileRepository.findById(id)
      .ifPresent(f -> {
        f.disabled();
        fileRepository.save(f);
      });
  }
}
