package com.exampledigisphere.vitapetcare.admin.timePeriod.repository;

import com.exampledigisphere.vitapetcare.admin.timePeriod.domain.TimePeriod;
import com.exampledigisphere.vitapetcare.config.root.Info;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Info(
  dev = Info.Dev.heltonOliveira,
  label = Info.Label.doc,
  date = "29/12/2025",
  description = "Repositório para a entidade TimePeriod"
)
public interface TimePeriodRepository extends JpaRepository<TimePeriod, Long> {
}
