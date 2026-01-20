package com.exampledigisphere.vitapetcare.admin.timePeriod.useCases;

import com.exampledigisphere.vitapetcare.admin.timePeriod.domain.TimePeriod;
import com.exampledigisphere.vitapetcare.admin.timePeriod.repository.TimePeriodRepository;
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
  description = "UseCase para salvar um período de tempo"
)
public class SaveTimePeriodUseCase {

  private final TimePeriodRepository timePeriodRepository;

  @Info(
    dev = Info.Dev.heltonOliveira,
    label = Info.Label.feature,
    date = "19/01/2026",
    description = "Salva um período de tempo e retorna sua visualização"
  )
  public Optional<TimePeriod> execute(@NonNull TimePeriod input) {
    log.info("Iniciando persistência do período de tempo: {}", input);

    return Optional.of(input)
      .map(timePeriodRepository::save);
  }
}
