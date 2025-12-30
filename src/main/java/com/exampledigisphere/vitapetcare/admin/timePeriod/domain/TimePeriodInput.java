package com.exampledigisphere.vitapetcare.admin.timePeriod.domain;

import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

public record TimePeriodInput(
  Long id,
  @NotNull LocalTime startTime,
  @NotNull LocalTime endTime,
  boolean _edited
) {
}
