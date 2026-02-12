package com.exampledigisphere.vitapetcare.admin.timePeriod;

import com.exampledigisphere.vitapetcare.admin.timePeriod.repository.TimePeriodRepository;
import com.exampledigisphere.vitapetcare.admin.workDay.WorkDayDTO;
import com.exampledigisphere.vitapetcare.config.root.Association;
import com.exampledigisphere.vitapetcare.config.root.Info;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static com.exampledigisphere.vitapetcare.config.root.Utils.initialize;
import static com.exampledigisphere.vitapetcare.config.root.Utils.transformToAssociationSet;

@Slf4j
@Service
@Transactional
@Info(
  dev = Info.Dev.heltonOliveira,
  label = Info.Label.feature,
  date = "06/02/2026",
  description = "Serviço para gestão semântica de períodos de tempo"
)
public class TimePeriodService {

  private final TimePeriodRepository timePeriodRepository;

  public TimePeriodService(final TimePeriodRepository timePeriodRepository) {
    this.timePeriodRepository = timePeriodRepository;
  }

  @Info(
    dev = Info.Dev.heltonOliveira,
    label = Info.Label.feature,
    date = "06/02/2026",
    description = "Aloca múltiplos períodos de tempo para um dia de trabalho"
  )
  public void allocate(@NonNull Set<TimePeriodDTO> periods, @NonNull WorkDayDTO workDay) {
    log.info("Alocando períodos para o dia de trabalho: {}", workDay.getId());

    periods.forEach(f -> this.define(f, Collections.emptySet()));
  }

  @Info(
    dev = Info.Dev.heltonOliveira,
    label = Info.Label.feature,
    date = "06/02/2026",
    description = "Define um período de tempo individualmente"
  )
  public Optional<TimePeriodDTO> define(@NonNull TimePeriodDTO input, Set<Association> associations) {
    log.info("Definindo período de tempo: {}", input);
    return TimePeriodFactory.createFrom(input)
      .map(timePeriodRepository::save)
      .map(tp -> tp.applyAssociations(transformToAssociationSet(associations)))
      .map(TimePeriodFactory::toResponse);
  }

  @Info(
    dev = Info.Dev.heltonOliveira,
    label = Info.Label.feature,
    date = "06/02/2026",
    description = "Recupera um período de tempo através de seu identificador"
  )
  @Transactional(readOnly = true)
  public Optional<TimePeriodDTO> retrieve(@NonNull Long id, Set<TimePeriodAssociations> associations) {
    log.info("Recuperando período de tempo ID: {}", id);

    return timePeriodRepository.findById(id)
      .map(wk -> wk.applyAssociations(transformToAssociationSet(associations)))
      .map(tp -> initialize(tp, associations))
      .map(TimePeriodFactory::toResponse);
  }

  @Info(
    dev = Info.Dev.heltonOliveira,
    label = Info.Label.feature,
    date = "06/02/2026",
    description = "Lista todos os períodos de tempo de forma paginada"
  )
  @Transactional(readOnly = true)
  public Page<TimePeriodDTO> list(@NonNull Pageable pageable) {
    log.info("Listando períodos de tempo paginados");
    return timePeriodRepository.findAll(pageable)
      .map(TimePeriodFactory::toResponse);
  }

  @Info(
    dev = Info.Dev.heltonOliveira,
    label = Info.Label.feature,
    date = "06/02/2026",
    description = "Descarta (desativa) um período de tempo"
  )
  public void discard(@NonNull Long id) {
    log.info("Descartando período de tempo ID: {}", id);
    timePeriodRepository.findById(id)
      .ifPresent(tp -> {
        tp.disabled();
        timePeriodRepository.save(tp);
      });
  }
}
