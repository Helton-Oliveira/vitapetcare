package com.exampledigisphere.vitapetcare.admin.workDay.useCases

import com.exampledigisphere.vitapetcare.admin.workDay.domain.WorkDay
import com.exampledigisphere.vitapetcare.admin.workDay.repository.WorkDayRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class GetAllWorkDay(
  private val workDayRepository: WorkDayRepository,
) {

  fun execute(page: Pageable): Result<Page<WorkDay>> = runCatching {
    workDayRepository.findAll(page)
  }
}
