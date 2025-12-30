package com.exampledigisphere.vitapetcare.admin.workDay.domain;

import com.exampledigisphere.vitapetcare.admin.timePeriod.domain.TimePeriodInput;
import jakarta.validation.constraints.NotNull;

import java.time.DayOfWeek;
import java.util.Set;

public record WorkDayInput(
  Long id,
  @NotNull DayOfWeek dayOfWeek,
  Long userId,
  Set<TimePeriodInput> shifts
) {
}
