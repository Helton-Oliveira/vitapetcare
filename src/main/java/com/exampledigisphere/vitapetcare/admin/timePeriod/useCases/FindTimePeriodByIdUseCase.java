package com.exampledigisphere.vitapetcare.admin.timePeriod.useCases;

import com.exampledigisphere.vitapetcare.admin.timePeriod.domain.TimePeriod;
import com.exampledigisphere.vitapetcare.admin.timePeriod.repository.TimePeriodRepository;
import com.exampledigisphere.vitapetcare.config.root.Info;
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
  description = "UseCase para buscar um dia de trabalho por ID"
)
public class FindTimePeriodByIdUseCase {

  private final TimePeriodRepository timePeriodRepository;

  @Info(
    dev = Info.Dev.heltonOliveira,
    label = Info.Label.feature,
    date = "19/01/2026",
    description = "Busca dia de trabalho por ID com associações"
  )
  public Optional<TimePeriod> execute(Long id) {
    log.info("FindTimePeriodByIdUseCase ID: {}", id);
    return timePeriodRepository.findById(id);
  }
}
