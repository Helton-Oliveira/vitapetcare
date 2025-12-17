package com.exampledigisphere.vitapetcare.admin.workDay.useCases

import com.exampledigisphere.vitapetcare.admin.timePeriod.useCases.SaveTimePeriod
import com.exampledigisphere.vitapetcare.admin.workDay.domain.WorkDay
import com.exampledigisphere.vitapetcare.admin.workDay.repository.WorkDayRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.util.Collections.emptySet

@Service
@Transactional
class SaveWorkDay(
  private val workDayRepository: WorkDayRepository,
  private val saveTimePeriod: SaveTimePeriod
) {

  fun execute(workDay: WorkDay): Result<WorkDay> = runCatching {
    val editedShifts = workDay.shifts
      ?.filter { it.wasEdited() }
      ?.toMutableSet()

    workDay.apply { this.shifts = emptySet() }
      .let(workDayRepository::save)
      .also { saveTimePeriod.execute(editedShifts, it) }
  }
}
