package com.exampledigisphere.vitapetcare.admin.timePeriod.domain;

import com.exampledigisphere.vitapetcare.admin.workDay.domain.WorkDay;
import com.exampledigisphere.vitapetcare.config.root.BaseEntity;
import com.exampledigisphere.vitapetcare.config.root.Info;
import jakarta.persistence.*;

import java.time.LocalTime;

@Entity
@Table(name = "wrk_times_periods")
@Info(
  dev = Info.Dev.heltonOliveira,
  label = Info.Label.doc,
  date = "29/12/2025",
  description = "Entidade que representa um período de tempo dentro de um dia de trabalho"
)
public class TimePeriod extends BaseEntity<TimePeriod> {

  @Transient
  public static final String TABLE_NAME = "wrk_times_periods";
  private LocalTime startTime;
  private LocalTime endTime;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "work_day_id")
  private WorkDay workDay;

  public TimePeriod() {
  }

  public LocalTime getStartTime() {
    return startTime;
  }

  public void setStartTime(LocalTime startTime) {
    this.startTime = startTime;
  }

  public LocalTime getEndTime() {
    return endTime;
  }

  public void setEndTime(LocalTime endTime) {
    this.endTime = endTime;
  }

  public WorkDay getWorkDay() {
    return workDay;
  }

  public void setWorkDay(WorkDay workDay) {
    this.workDay = workDay;
  }

  public interface Authority {
    String TIME_PERIOD_CREATE = "TIME_PERIOD_CREATE";
    String TIME_PERIOD_EDIT = "TIME_PERIOD_EDIT";
    String TIME_PERIOD_VIEW = "TIME_PERIOD_VIEW";
    String TIME_PERIOD_VIEW_LIST = "TIME_PERIOD_VIEW_LIST";
    String TIME_PERIOD_DELETE = "TIME_PERIOD_DELETE";
  }
}
