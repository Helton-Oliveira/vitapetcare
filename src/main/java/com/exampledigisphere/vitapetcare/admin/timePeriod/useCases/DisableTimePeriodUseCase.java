package com.exampledigisphere.vitapetcare.admin.timePeriod.useCases;

import com.exampledigisphere.vitapetcare.admin.timePeriod.repository.TimePeriodRepository;
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
public class DisableTimePeriodUseCase {

  private final TimePeriodRepository timePeriodRepository;

  @Info(
    dev = Info.Dev.heltonOliveira,
    label = Info.Label.feature,
    date = "19/01/2026",
    description = "Desativa um periodo de trabalho (soft delete)"
  )
  public void execute(Long id) {
    log.info("DisableTimePeriodUseCase ID: {}", id);
    timePeriodRepository.findById(id).ifPresent(workDay -> {
      workDay.disabled();
      timePeriodRepository.save(workDay);
    });
  }
}
