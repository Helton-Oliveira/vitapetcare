package com.exampledigisphere.vitapetcare.admin.workDay.useCases

import com.exampledigisphere.vitapetcare.admin.workDay.repository.WorkDayRepository
import org.springframework.stereotype.Service

@Service
class DisableWorkDay(
  private val workDayRepository: WorkDayRepository,
) {

  fun execute(id: Long): Result<*> =
    runCatching {
      val user = workDayRepository.findById(id)
        .takeIf { it.isPresent }
        ?.get()

      user?.disabled();

      user.takeIf { it != null }
        ?.let(workDayRepository::save)
    }
}
