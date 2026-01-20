package com.exampledigisphere.vitapetcare.admin.workDay.useCases;

import com.exampledigisphere.vitapetcare.admin.workDay.repository.WorkDayRepository;
import com.exampledigisphere.vitapetcare.config.root.Info;
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
  description = "UseCase para desativar um dia de trabalho"
)
public class DisableWorkDayUseCase {

  private final WorkDayRepository workDayRepository;

  @Info(
    dev = Info.Dev.heltonOliveira,
    label = Info.Label.feature,
    date = "19/01/2026",
    description = "Desativa um dia de trabalho (soft delete)"
  )
  public void execute(Long id) {
    log.info("Desativando dia de trabalho ID: {}", id);
    workDayRepository.findById(id).ifPresent(workDay -> {
      workDay.disabled();
      workDayRepository.save(workDay);
    });
  }
}
