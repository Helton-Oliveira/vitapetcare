package com.exampledigisphere.vitapetcare.admin.workDay.domain;

import com.exampledigisphere.vitapetcare.admin.timePeriod.domain.TimePeriodMapper;
import com.exampledigisphere.vitapetcare.config.root.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class WorkDayMapper implements Mapper<WorkDayInput, WorkDay, WorkDayOutput> {

  private final TimePeriodMapper timePeriodMapper;

  @Override
  public WorkDay toDomain(WorkDayInput input) {
    if (input == null) {
      return null;
    }
    WorkDay workDay = new WorkDay();
    workDay.setId(input.id());
    workDay.setDayOfWeek(input.dayOfWeek());
    if (input.shifts() != null) {
      workDay.setShifts(input.shifts().stream()
        .map(timePeriodMapper::toDomain)
        .collect(Collectors.toSet()));
    }
    return workDay;
  }

  @Override
  public WorkDayOutput toOutput(WorkDay domain) {
    if (domain == null) {
      return null;
    }
    return new WorkDayOutput(
      domain.getId(),
      domain.getDayOfWeek(),
      domain.getUser() != null ? domain.getUser().getId() : null,
      domain.getShifts() != null ? domain.getShifts().stream()
        .map(timePeriodMapper::toOutput)
        .collect(Collectors.toSet()) : null,
      domain.isActive()
    );
  }
}
