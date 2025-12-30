package com.exampledigisphere.vitapetcare.admin.workDay.domain;

import com.exampledigisphere.vitapetcare.admin.timePeriod.domain.TimePeriodOutput;

import java.time.DayOfWeek;
import java.util.Set;

public record WorkDayOutput(
  Long id,
  DayOfWeek dayOfWeek,
  Long userId,
  Set<TimePeriodOutput> shifts,
  boolean active
) {
}
