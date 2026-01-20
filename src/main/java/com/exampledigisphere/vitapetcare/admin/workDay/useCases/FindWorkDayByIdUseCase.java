package com.exampledigisphere.vitapetcare.admin.workDay.useCases;

import com.exampledigisphere.vitapetcare.admin.workDay.domain.WorkDay;
import com.exampledigisphere.vitapetcare.admin.workDay.repository.WorkDayRepository;
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
public class FindWorkDayByIdUseCase {

  private final WorkDayRepository workDayRepository;

  @Info(
    dev = Info.Dev.heltonOliveira,
    label = Info.Label.feature,
    date = "19/01/2026",
    description = "Busca dia de trabalho por ID com associações"
  )
  public Optional<WorkDay> execute(Long id) {
    log.info("Buscando dia de trabalho por ID: {}", id);
    return workDayRepository.findById(id);
  }
}
