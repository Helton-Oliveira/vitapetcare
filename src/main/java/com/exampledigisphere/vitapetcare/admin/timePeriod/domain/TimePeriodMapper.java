package com.exampledigisphere.vitapetcare.admin.timePeriod.domain;

import com.exampledigisphere.vitapetcare.config.root.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class TimePeriodMapper implements Mapper<TimePeriodInput, TimePeriod, TimePeriodOutput> {

  @Override
  public TimePeriod toDomain(TimePeriodInput input) {
    if (input == null) {
      return null;
    }
    TimePeriod timePeriod = new TimePeriod();
    timePeriod.setId(input.id());
    timePeriod.setStartTime(input.startTime());
    timePeriod.setEndTime(input.endTime());
    timePeriod.set_edited(input._edited());
    return timePeriod;
  }

  @Override
  public TimePeriodOutput toOutput(TimePeriod domain) {
    if (domain == null) {
      return null;
    }
    return new TimePeriodOutput(
      domain.getId(),
      domain.getStartTime(),
      domain.getEndTime(),
      domain.isActive()
    );
  }
}
