package com.exampledigisphere.vitapetcare.admin.timePeriod;

import com.exampledigisphere.vitapetcare.admin.workDay.WorkDayDTO;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalTime;

public class TimePeriodDTO implements Serializable {
  private static final long serialVersionUID = 1L;

  @JsonView(Json.List.class)
  private Long id;

  @JsonView(Json.List.class)
  private String uuid;

  @NotNull
  @JsonView(Json.List.class)
  private LocalTime startTime;

  @NotNull
  @JsonView(Json.List.class)
  private LocalTime endTime;

  @JsonView(Json.WithWorkDay.class)
  private WorkDayDTO workDay;

  @NotNull
  @JsonView(Json.List.class)
  private Boolean edited;

  @NotNull
  @JsonView(Json.List.class)
  private Boolean active;

  public TimePeriodDTO() {
  }

  public TimePeriodDTO(Long id, String uuid, LocalTime startTime, LocalTime endTime, WorkDayDTO workDay, Boolean edited, Boolean active) {
    this.id = id;
    this.uuid = uuid;
    this.startTime = startTime;
    this.endTime = endTime;
    this.workDay = workDay;
    this.edited = edited;
    this.active = active;
  }

  public Long getId() {
    return id;
  }

  public String getUuid() {
    return uuid;
  }

  public LocalTime getStartTime() {
    return startTime;
  }

  public LocalTime getEndTime() {
    return endTime;
  }

  public WorkDayDTO getWorkDay() {
    return workDay;
  }

  public Boolean getEdited() {
    return edited;
  }

  public Boolean getActive() {
    return active;
  }

  public void setWorkDay(WorkDayDTO workDay) {
    this.workDay = workDay;
  }

  public interface Json {
    interface List {
    }

    interface Detail extends List {
    }

    interface WithWorkDay extends WorkDayDTO.Json.List {
    }

    interface All extends Detail, WithWorkDay {
    }
  }
}
