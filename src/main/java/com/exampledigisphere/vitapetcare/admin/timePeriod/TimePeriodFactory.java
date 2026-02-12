package com.exampledigisphere.vitapetcare.admin.timePeriod;

import com.exampledigisphere.vitapetcare.admin.timePeriod.domain.TimePeriod;
import com.exampledigisphere.vitapetcare.admin.workDay.WorkDayDTO;
import com.exampledigisphere.vitapetcare.admin.workDay.WorkDayFactory;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class TimePeriodFactory {

  public static Optional<TimePeriod> createFrom(TimePeriodDTO input) {
    log.info("createFrom, {}", input);

    TimePeriod timePeriod = new TimePeriod();
    timePeriod.setId(input.getId());
    timePeriod.setStartTime(input.getStartTime());
    timePeriod.setEndTime(input.getEndTime());
    WorkDayFactory.createFrom(input.getWorkDay());
    timePeriod.setEdited(input.getEdited());
    timePeriod.setActive(input.getActive());

    return Optional.of(timePeriod);
  }

  public static TimePeriodDTO toResponse(TimePeriod domain) {
    log.info("toResponse, {}", domain);

    return new TimePeriodDTO(
      domain.getId(),
      domain.getUuid(),
      domain.getStartTime(),
      domain.getEndTime(),
      toWorkDayDTO(domain),
      domain.isEdited(),
      domain.isActive()
    );
  }

  public static Set<TimePeriodDTO> toResponseList(Set<TimePeriod> domain) {
    log.info("toResponseList, {}", domain);

    return domain.stream().map(TimePeriodFactory::toResponse).collect(java.util.stream.Collectors.toSet());
  }

  private static WorkDayDTO toWorkDayDTO(TimePeriod domain) {
    if (domain == null || domain.getWorkDay() == null || !Hibernate.isInitialized(domain.getWorkDay())) return null;
    return WorkDayFactory.toResponse(domain.getWorkDay());
  }
}
