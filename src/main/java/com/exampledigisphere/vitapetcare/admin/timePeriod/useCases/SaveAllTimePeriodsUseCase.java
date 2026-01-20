package com.exampledigisphere.vitapetcare.admin.timePeriod.useCases;

import com.exampledigisphere.vitapetcare.admin.timePeriod.domain.TimePeriod;
import com.exampledigisphere.vitapetcare.admin.workDay.domain.WorkDay;
import com.exampledigisphere.vitapetcare.config.root.Info;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
@Info(
  dev = Info.Dev.heltonOliveira,
  label = Info.Label.feature,
  date = "19/01/2026",
  description = "UseCase para salvar múltiplos períodos de tempo"
)
public class SaveAllTimePeriodsUseCase {

  private final SaveTimePeriodUseCase saveTimePeriodUseCase;

  @Info(
    dev = Info.Dev.heltonOliveira,
    label = Info.Label.feature,
    date = "19/01/2026",
    description = "Salva todos os períodos vinculados a um dia de trabalho"
  )
  public void execute(@NonNull Set<TimePeriod> periods, @NonNull WorkDay workDay) {
    log.info("Salvando lista de períodos de tempo para o dia de trabalho: {}", workDay);

    periods.stream()
      .peek(tp -> tp.setWorkDay(workDay))
      .forEach(saveTimePeriodUseCase::execute);
  }
}
