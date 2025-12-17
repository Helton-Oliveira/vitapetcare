package com.exampledigisphere.vitapetcare.admin.workDay.domain

import com.exampledigisphere.vitapetcare.admin.timePeriod.domain.TimePeriod
import com.exampledigisphere.vitapetcare.admin.user.domain.User
import com.exampledigisphere.vitapetcare.config.root.BaseEntity
import com.exampledigisphere.vitapetcare.config.security.Permissions
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonView
import jakarta.persistence.*
import org.hibernate.Hibernate
import org.jetbrains.annotations.NotNull
import java.time.LocalDate
import java.util.Collections.emptySet

@Entity
@Table(name = "wrk_works_days")
class WorkDay : BaseEntity() {

  @field:Transient
  final val TABLE_NAME = "wrk_works_days";

  @field:NotNull
  @field:JsonView(Json.List::class)
  lateinit var date: LocalDate;

  @field:NotNull
  @field:JsonView(Json.Detail::class)
  @field:ManyToOne(fetch = FetchType.LAZY)
  lateinit var user: User

  @field:NotNull
  @field:OneToMany(fetch = FetchType.LAZY)
  @field:JsonIgnoreProperties("dayOfWeeks")
  var shifts: MutableSet<TimePeriod>? = emptySet();

  // colocar depois que criar os agendamentos
  //lateinit var  appointments: MutableList<Appointment> = mutableListOf()

  fun loadUser() = Hibernate.initialize(user);

  fun loadShifts() = Hibernate.initialize(shifts);

  interface Json {
    interface List;
    interface Detail : List;
    interface All : Detail;
  }

  object WorkDayPermissions : Permissions {
    override var resource = "WORK_DAY";
  }
}

