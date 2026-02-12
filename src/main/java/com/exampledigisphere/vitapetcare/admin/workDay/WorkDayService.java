package com.exampledigisphere.vitapetcare.admin.workDay;

import com.exampledigisphere.vitapetcare.admin.timePeriod.TimePeriodService;
import com.exampledigisphere.vitapetcare.admin.user.UserDTO;
import com.exampledigisphere.vitapetcare.admin.workDay.repository.WorkDayRepository;
import com.exampledigisphere.vitapetcare.config.root.Info;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

import static com.exampledigisphere.vitapetcare.config.root.Utils.initialize;
import static com.exampledigisphere.vitapetcare.config.root.Utils.transformToAssociationSet;
import static java.util.Collections.emptySet;

@Slf4j
@Service
@Transactional
@Info(
  dev = Info.Dev.heltonOliveira,
  label = Info.Label.feature,
  date = "06/02/2026",
  description = "Serviço para gestão semântica de dias de trabalho"
)
public class WorkDayService {

  private final WorkDayRepository workDayRepository;
  private final TimePeriodService timePeriodService;

  public WorkDayService(final WorkDayRepository workDayRepository, final TimePeriodService timePeriodService) {
    this.workDayRepository = workDayRepository;
    this.timePeriodService = timePeriodService;
  }

  @Info(
    dev = Info.Dev.heltonOliveira,
    label = Info.Label.feature,
    date = "06/02/2026",
    description = "Atribui múltiplos dias de trabalho a um usuário"
  )
  public void assign(@NonNull Set<WorkDayDTO> workDays, @NonNull UserDTO user) {
    log.info("Atribuindo {} dias de trabalho para o usuário: {}", workDays.size(), user.getId());

    workDays.stream()
      .peek(wk -> wk.setUser(user))
      .forEach(wk -> this.schedule(wk, emptySet()));
  }

  @Info(
    dev = Info.Dev.heltonOliveira,
    label = Info.Label.feature,
    date = "06/02/2026",
    description = "Agenda ou atualiza um dia de trabalho individualmente"
  )
  public Optional<WorkDayDTO> schedule(@NonNull WorkDayDTO input, Set<WorkDayAssociations> associations) {
    log.info("Agendando dia de trabalho: {}", input);

    return Optional.of(WorkDayFactory.createFrom(input))
      .map(workDayRepository::save)
      .map(wk -> wk.applyAssociations(transformToAssociationSet(associations)))
      .map(WorkDayFactory::toResponse)
      .map(savedWk -> persistDependencies(input, savedWk));
  }

  @Info(
    dev = Info.Dev.heltonOliveira,
    label = Info.Label.feature,
    date = "06/02/2026",
    description = "Recupera um dia de trabalho através de seu identificador"
  )
  @Transactional(readOnly = true)
  public Optional<WorkDayDTO> retrieve(@NonNull Long id, Set<WorkDayAssociations> associations) {
    log.info("Recuperando dia de trabalho ID: {}", id);

    return workDayRepository.findById(id)
      .map(wk -> wk.applyAssociations(transformToAssociationSet(associations)))
      .map(wk -> initialize(wk, associations))
      .map(WorkDayFactory::toResponse);
  }

  @Info(
    dev = Info.Dev.heltonOliveira,
    label = Info.Label.feature,
    date = "06/02/2026",
    description = "Lista todos os dias de trabalho registrados de forma paginada"
  )
  @Transactional(readOnly = true)
  public Page<WorkDayDTO> list(@NonNull Pageable pageable) {
    log.info("Listando dias de trabalho paginados");

    return workDayRepository.findAll(pageable)
      .map(WorkDayFactory::toResponse);
  }

  @Info(
    dev = Info.Dev.heltonOliveira,
    label = Info.Label.feature,
    date = "06/02/2026",
    description = "Interrompe (desativa) um dia de trabalho"
  )
  public void suspend(@NonNull Long id) {
    log.info("Interrompendo dia de trabalho ID: {}", id);
    workDayRepository.findById(id)
      .ifPresent(wk -> {
        wk.disabled();
        workDayRepository.save(wk);
      });
  }

  private WorkDayDTO persistDependencies(WorkDayDTO input, WorkDayDTO savedWorkDay) {
    input.editedShifts().ifPresent(shifts -> timePeriodService.allocate(shifts, savedWorkDay));
    return savedWorkDay;
  }
}
