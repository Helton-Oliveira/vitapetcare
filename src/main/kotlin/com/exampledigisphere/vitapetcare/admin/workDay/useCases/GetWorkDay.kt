package com.exampledigisphere.vitapetcare.admin.workDay.useCases

import com.exampledigisphere.vitapetcare.admin.workDay.domain.WorkDay
import com.exampledigisphere.vitapetcare.admin.workDay.repository.WorkDayRepository
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class GetWorkDay(
  private val workDayRepository: WorkDayRepository,
) {

  fun execute(id: Long): Result<WorkDay?> = runCatching {
    workDayRepository.findById(id).getOrNull()
  }
}
