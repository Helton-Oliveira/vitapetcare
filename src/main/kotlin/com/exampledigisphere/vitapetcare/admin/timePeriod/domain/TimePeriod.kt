package com.exampledigisphere.vitapetcare.admin.timePeriod.domain

import com.exampledigisphere.vitapetcare.admin.workDay.domain.WorkDay
import com.exampledigisphere.vitapetcare.config.root.BaseEntity
import com.exampledigisphere.vitapetcare.config.security.Permissions
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonView
import jakarta.persistence.*
import org.hibernate.Hibernate
import org.jetbrains.annotations.NotNull
import java.time.LocalTime

@Entity
@Table(name = "wrk_times_periods")
class TimePeriod : BaseEntity() {

  @field:Transient
  final val TABLE_NAME = "wrk_times_periods";

  @field:NotNull
  @field:JsonView(Json.List::class)
  lateinit var startTime: LocalTime;

  @field:NotNull
  @field:JsonView(Json.List::class)
  lateinit var endTime: LocalTime;

  @field:NotNull
  @field:ManyToOne(fetch = FetchType.LAZY)
  @field:JoinColumn(name = "work_day_id")
  @field:JsonView(Json.WithWorkDay::class)
  @field:JsonIgnoreProperties("shifts")
  lateinit var workDay: WorkDay;

  fun loadWorkDay() = Hibernate.initialize(workDay);

  interface Json {
    interface List;
    interface Detail : List;
    interface WithWorkDay : WorkDay.Json.List;
    interface All : Detail, WithWorkDay;
  }

  object TimePeriodPermissions : Permissions {
    override var resource = "TIME_PERIOD";
  }
}
