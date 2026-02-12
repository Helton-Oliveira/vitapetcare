package com.exampledigisphere.vitapetcare.admin.workDay.domain;

import com.exampledigisphere.vitapetcare.admin.timePeriod.domain.TimePeriod;
import com.exampledigisphere.vitapetcare.admin.user.domain.User;
import com.exampledigisphere.vitapetcare.config.root.BaseEntity;
import com.exampledigisphere.vitapetcare.config.root.Info;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "wrk_works_days")
@Info(
  dev = Info.Dev.heltonOliveira,
  label = Info.Label.doc,
  date = "29/12/2025",
  description = "Entidade que representa o dia de trabalho de um usuário"
)
public class WorkDay extends BaseEntity<WorkDay> {

  @Transient
  public static final String TABLE_NAME = "wrk_works_days";

  @NotNull
  @Enumerated(EnumType.STRING)
  private DayOfWeek dayOfWeek;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "workDay", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnoreProperties("workDay")
  private Set<TimePeriod> shifts = new HashSet<>();

  public WorkDay() {
  }

  public DayOfWeek getDayOfWeek() {
    return dayOfWeek;
  }

  public void setDayOfWeek(DayOfWeek dayOfWeek) {
    this.dayOfWeek = dayOfWeek;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Set<TimePeriod> getShifts() {
    return shifts;
  }

  public void setShifts(Set<TimePeriod> shifts) {
    this.shifts = shifts;
  }

  public interface Authority {
    String WORK_DAY_CREATE = "WORK_DAY_CREATE";
    String WORK_DAY_EDIT = "WORK_DAY_EDIT";
    String WORK_DAY_VIEW = "WORK_DAY_VIEW";
    String WORK_DAY_VIEW_LIST = "WORK_DAY_VIEW_LIST";
    String WORK_DAY_DELETE = "WORK_DAY_DELETE";
  }
}
