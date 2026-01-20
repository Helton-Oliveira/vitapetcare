package com.exampledigisphere.vitapetcare.admin.workDay.useCases;

import com.exampledigisphere.vitapetcare.admin.workDay.domain.WorkDay;
import com.exampledigisphere.vitapetcare.admin.workDay.repository.WorkDayRepository;
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
public class FindAllWorkDaysUseCase {

  private final WorkDayRepository workDayRepository;

  @Info(
    dev = Info.Dev.heltonOliveira,
    label = Info.Label.feature,
    date = "19/01/2026",
    description = "Busca todos os dias de trabalho com paginação e associações"
  )
  public Page<WorkDay> execute(Pageable page) {
    log.info("Buscando todos os dias de trabalho");
    return workDayRepository.findAll(page);
  }
}
