package com.exampledigisphere.vitapetcare.admin.timePeriod.useCases

import com.exampledigisphere.vitapetcare.admin.timePeriod.domain.TimePeriod
import com.exampledigisphere.vitapetcare.admin.timePeriod.repository.TimePeriodRepository
import com.exampledigisphere.vitapetcare.admin.workDay.domain.WorkDay
import org.springframework.stereotype.Service

@Service
class SaveTimePeriod(
  private val timePeriodRepository: TimePeriodRepository,
) {

  fun execute(timePeriods: MutableSet<TimePeriod>?, workDay: WorkDay) =
    timePeriods?.map { timePeriod -> timePeriod.apply { this.workDay = workDay } }
      ?.map(timePeriodRepository::save)

}
