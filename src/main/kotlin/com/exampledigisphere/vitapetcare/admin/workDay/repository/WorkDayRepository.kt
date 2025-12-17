package com.exampledigisphere.vitapetcare.admin.workDay.repository

import com.exampledigisphere.vitapetcare.admin.workDay.domain.WorkDay
import org.springframework.data.jpa.repository.JpaRepository

interface WorkDayRepository : JpaRepository<WorkDay, Long> {
}
