package com.exampledigisphere.vitapetcare.admin.timePeriod.repository

import com.exampledigisphere.vitapetcare.admin.timePeriod.domain.TimePeriod
import org.springframework.data.jpa.repository.JpaRepository

interface TimePeriodRepository : JpaRepository<TimePeriod, Long> {
}
