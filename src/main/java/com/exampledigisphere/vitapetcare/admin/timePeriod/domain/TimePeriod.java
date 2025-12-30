package com.exampledigisphere.vitapetcare.admin.timePeriod.domain;

import com.exampledigisphere.vitapetcare.admin.workDay.domain.WorkDay;
import com.exampledigisphere.vitapetcare.config.root.BaseEntity;
import com.exampledigisphere.vitapetcare.config.root.Info;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Entity
@Table(name = "wrk_times_periods")
@Getter
@Setter
@Info(
  dev = Info.Dev.heltonOliveira,
  label = Info.Label.doc,
  date = "29/12/2025",
  description = "Entidade que representa um período de tempo dentro de um dia de trabalho"
)
public class TimePeriod extends BaseEntity {

  @Transient
  public static final String TABLE_NAME = "wrk_times_periods";

  @NotNull
  @JsonView(Json.List.class)
  private LocalTime startTime;

  @NotNull
  @JsonView(Json.List.class)
  private LocalTime endTime;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "work_day_id")
  @JsonView(Json.WithWorkDay.class)
  @JsonIgnoreProperties("shifts")
  private WorkDay workDay;

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

  public interface Json {
    interface List {
    }

    interface Detail extends List {
    }

    interface WithWorkDay extends WorkDay.Json.List {
    }

    interface All extends Detail, WithWorkDay {
    }
  }
}
