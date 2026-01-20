package com.exampledigisphere.vitapetcare.admin.workDay.useCases;

import com.exampledigisphere.vitapetcare.admin.user.domain.User;
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
  description = "UseCase para salvar múltiplos dias de trabalho"
)
public class SaveAllWorkDaysUseCase {

  private final SaveWorkDayUseCase saveWorkDayUseCase;

  @Info(
    dev = Info.Dev.heltonOliveira,
    label = Info.Label.feature,
    date = "19/01/2026",
    description = "Salva todos os dias de trabalho vinculados a um usuário"
  )
  public void execute(@NonNull Set<WorkDay> workDays, @NonNull User user) {
    log.info("SaveAllWorkDaysUseCase: {}, {}", workDays, user);

    workDays.stream()
      .peek(wk -> wk.setUser(user))
      .forEach(saveWorkDayUseCase::execute);
  }
}
