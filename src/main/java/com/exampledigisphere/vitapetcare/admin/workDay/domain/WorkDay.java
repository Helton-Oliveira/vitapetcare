package com.exampledigisphere.vitapetcare.admin.workDay.domain;

import com.exampledigisphere.vitapetcare.admin.timePeriod.domain.TimePeriod;
import com.exampledigisphere.vitapetcare.admin.user.domain.User;
import com.exampledigisphere.vitapetcare.config.root.BaseEntity;
import com.exampledigisphere.vitapetcare.config.root.Info;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "wrk_works_days")
@Getter
@Setter
@Info(
  dev = Info.Dev.heltonOliveira,
  label = Info.Label.doc,
  date = "29/12/2025",
  description = "Entidade que representa o dia de trabalho de um usuário"
)
public class WorkDay extends BaseEntity {

  @Transient
  public static final String TABLE_NAME = "wrk_works_days";

  @NotNull
  @Enumerated(EnumType.STRING)
  @JsonView(Json.List.class)
  private DayOfWeek dayOfWeek;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  @JsonView(Json.Detail.class)
  private User user;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "workDay", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnoreProperties("workDay")
  @JsonView(Json.All.class)
  private Set<TimePeriod> shifts = new HashSet<>();

  public WorkDay loadShifts() {
    Hibernate.initialize(shifts);
    return this;
  }

  public WorkDay loadUser() {
    Hibernate.initialize(user);
    return this;
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

  public interface Json {
    interface List {
    }

    interface Detail extends List {
    }

    interface All extends Detail {
    }
  }
}
