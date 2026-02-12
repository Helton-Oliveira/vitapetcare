package com.exampledigisphere.vitapetcare.admin.workDay;

import com.exampledigisphere.vitapetcare.admin.timePeriod.TimePeriodDTO;
import com.exampledigisphere.vitapetcare.admin.user.UserDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class WorkDayDTO implements Serializable {
  private static final long serialVersionUID = 1L;

  @JsonView(Json.List.class)
  private Long id;

  @JsonView(Json.List.class)
  private String uuid;

  @NotNull
  @JsonView(Json.List.class)
  private DayOfWeek dayOfWeek;

  @JsonView(Json.Detail.class)
  private UserDTO user;

  @JsonView(Json.WithTimePeriod.class)
  @JsonIgnoreProperties("workDay")
  private Set<TimePeriodDTO> shifts;

  @NotNull
  @JsonView(Json.List.class)
  private Boolean edited;

  @NotNull
  @JsonView(Json.List.class)
  private Boolean active;

  public WorkDayDTO() {
  }

  public WorkDayDTO(Long id, String uuid, DayOfWeek dayOfWeek, UserDTO user, Set<TimePeriodDTO> shifts, Boolean edited, Boolean active) {
    this.id = id;
    this.uuid = uuid;
    this.dayOfWeek = dayOfWeek;
    this.user = user;
    this.shifts = shifts;
    this.edited = edited;
    this.active = active;
  }

  public Long getId() {
    return id;
  }

  public String getUuid() {
    return uuid;
  }

  public DayOfWeek getDayOfWeek() {
    return dayOfWeek;
  }

  public UserDTO getUser() {
    return user;
  }

  public Set<TimePeriodDTO> getShifts() {
    return shifts;
  }

  public Boolean getEdited() {
    return edited;
  }

  public Boolean getActive() {
    return active;
  }

  public void setUser(UserDTO user) {
    this.user = user;
  }

  public void setShifts(Set<TimePeriodDTO> shifts) {
    this.shifts = shifts;
  }

  public Optional<Set<TimePeriodDTO>> editedShifts() {
    return Optional.ofNullable(shifts)
      .map(s -> s.stream()
        .filter(Objects::nonNull)
        .filter(TimePeriodDTO::getEdited)
        .collect(Collectors.toSet()));
  }

  public interface Json {
    interface List {
    }

    interface Detail extends List {
    }

    interface WithTimePeriod extends TimePeriodDTO.Json.List {
    }

    interface All extends Detail, WithTimePeriod {
    }
  }
}
