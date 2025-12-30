package com.exampledigisphere.vitapetcare.admin.workDay.service;

import com.exampledigisphere.vitapetcare.admin.timePeriod.domain.TimePeriod;
import com.exampledigisphere.vitapetcare.admin.timePeriod.repository.TimePeriodRepository;
import com.exampledigisphere.vitapetcare.admin.user.domain.User;
import com.exampledigisphere.vitapetcare.admin.workDay.domain.WorkDay;
import com.exampledigisphere.vitapetcare.admin.workDay.domain.WorkDayInput;
import com.exampledigisphere.vitapetcare.admin.workDay.domain.WorkDayMapper;
import com.exampledigisphere.vitapetcare.admin.workDay.domain.WorkDayOutput;
import com.exampledigisphere.vitapetcare.admin.workDay.repository.WorkDayRepository;
import com.exampledigisphere.vitapetcare.config.root.Info;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@Info(
  dev = Info.Dev.heltonOliveira,
  label = Info.Label.feature,
  date = "29/12/2025",
  description = "Serviço consolidado para gestão de dias de trabalho e períodos"
)
public class WorkDayService {

  private final WorkDayRepository workDayRepository;
  private final TimePeriodRepository timePeriodRepository;
  private final WorkDayMapper workDayMapper;

  public WorkDayService(final WorkDayRepository workDayRepository,
                        final TimePeriodRepository timePeriodRepository,
                        final WorkDayMapper workDayMapper) {
    this.workDayRepository = workDayRepository;
    this.timePeriodRepository = timePeriodRepository;
    this.workDayMapper = workDayMapper;
  }

  public Optional<WorkDayOutput> save(WorkDayInput input) {
    log.info("Salvando dia de trabalho: {}", input.dayOfWeek());

    WorkDay workDay = workDayMapper.toDomain(input);

    Set<TimePeriod> editedShifts = Optional.ofNullable(workDay.getShifts())
      .orElse(new HashSet<>())
      .stream()
      .filter(TimePeriod::wasEdited)
      .collect(Collectors.toSet());

    // Desacopla shifts para salvar o workDay primeiro
    workDay.setShifts(new HashSet<>());
    WorkDay savedWorkDay = workDayRepository.save(workDay);

    // Salva shifts associados
    editedShifts.forEach(shift -> {
      shift.setWorkDay(savedWorkDay);
      timePeriodRepository.save(shift);
    });

    savedWorkDay.setShifts(editedShifts);
    return Optional.of(savedWorkDay).map(workDayMapper::toOutput);
  }

  public List<WorkDayOutput> findAll() {
    log.info("Buscando todos os dias de trabalho");
    return workDayRepository.findAll().stream()
      .map(workDayMapper::toOutput)
      .collect(Collectors.toList());
  }

  public Optional<WorkDayOutput> getById(Long id) {
    log.info("Buscando dia de trabalho por ID: {}", id);
    return workDayRepository.findById(id)
      .map(WorkDay::loadShifts)
      .map(workDayMapper::toOutput);
  }

  public void disable(Long id) {
    log.info("Desativando dia de trabalho ID: {}", id);
    workDayRepository.findById(id).ifPresent(workDay -> {
      workDay.disabled();
      workDayRepository.save(workDay);
    });
  }

  public void saveAllAndFlush(List<WorkDayInput> workDays) {
    log.info("Salvando lista de dias de trabalho");
    workDays.forEach(this::save);
    workDayRepository.flush();
  }

  /**
   * Método utilitário para salvar múltiplos dias de trabalho associados a um usuário.
   */
  public void saveAllForUser(Set<WorkDay> workDays, User user) {
    workDays.forEach(workDay -> {
      workDay.setUser(user);
      workDayRepository.save(workDay);
    });
  }
}
