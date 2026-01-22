package com.exampledigisphere.vitapetcare.admin.timePeriod.useCases;

import com.exampledigisphere.vitapetcare.admin.timePeriod.domain.TimePeriod;
import com.exampledigisphere.vitapetcare.admin.timePeriod.repository.TimePeriodRepository;
import com.exampledigisphere.vitapetcare.config.root.Info;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
@Info(
  dev = Info.Dev.heltonOliveira,
  label = Info.Label.feature,
  date = "19/01/2026",
  description = "UseCase para listar todos os dias de trabalho"
)
public class FindAllTimePeriodsUseCase {

  private final TimePeriodRepository timePeriodRepository;

  @Info(
    dev = Info.Dev.heltonOliveira,
    label = Info.Label.feature,
    date = "19/01/2026",
    description = "Busca todos os periodos de trabalho com paginação"
  )
  public Page<TimePeriod> execute(Pageable page) {
    log.info("FindAllTimePeriodUseCase");

    return timePeriodRepository.findAll(page);
  }
}
