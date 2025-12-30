package com.exampledigisphere.vitapetcare.catalog.service.repository;

import com.exampledigisphere.vitapetcare.catalog.service.domain.Service;
import com.exampledigisphere.vitapetcare.config.root.Info;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Info(
  dev = Info.Dev.heltonOliveira,
  label = Info.Label.doc,
  date = "29/12/2025",
  description = "Repositório para a entidade Service"
)
public interface ServiceRepository extends JpaRepository<Service, Long> {
}
