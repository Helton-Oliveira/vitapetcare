package com.exampledigisphere.vitapetcare.admin.workDay.useCases;

import com.exampledigisphere.vitapetcare.admin.timePeriod.domain.TimePeriod;
import com.exampledigisphere.vitapetcare.admin.timePeriod.useCases.SaveAllTimePeriodsUseCase;
import com.exampledigisphere.vitapetcare.admin.workDay.domain.WorkDay;
import com.exampledigisphere.vitapetcare.admin.workDay.repository.WorkDayRepository;
import com.exampledigisphere.vitapetcare.config.root.Info;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
@Info(
  dev = Info.Dev.heltonOliveira,
  label = Info.Label.feature,
  date = "19/01/2026",
  description = "UseCase para salvar ou atualizar um dia de trabalho"
)
public class SaveWorkDayUseCase {

  private final WorkDayRepository workDayRepository;
  private final SaveAllTimePeriodsUseCase saveAllTimePeriodsUseCase;

  @Info(
    dev = Info.Dev.heltonOliveira,
    label = Info.Label.feature,
    date = "19/01/2026",
    description = "Salva um dia de trabalho e processa seus períodos"
  )
  public Optional<WorkDay> execute(@NonNull WorkDay input) {
    log.info("Iniciando persistência do dia de trabalho: {}", input);

    final var editedTimePeriods = input.getShifts().stream()
      .filter(Objects::nonNull)
      .filter(TimePeriod::isEdited)
      .collect(Collectors.toSet());

    return Optional.of(input).stream()
      .peek(WorkDay::prepareToCreation)
      .map(workDayRepository::save)
      .peek(wk -> saveAllTimePeriodsUseCase.execute(editedTimePeriods, wk))
      .findFirst();
  }
}
