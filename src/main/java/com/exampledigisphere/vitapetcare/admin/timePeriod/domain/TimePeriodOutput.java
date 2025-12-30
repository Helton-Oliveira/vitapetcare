package com.exampledigisphere.vitapetcare.admin.timePeriod.domain;

import java.time.LocalTime;

public record TimePeriodOutput(
  Long id,
  LocalTime startTime,
  LocalTime endTime,
  boolean active
) {
}
