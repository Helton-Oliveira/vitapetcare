package com.exampledigisphere.vitapetcare.catalog.service.service;

import com.exampledigisphere.vitapetcare.catalog.service.domain.Service;
import com.exampledigisphere.vitapetcare.catalog.service.repository.ServiceRepository;
import com.exampledigisphere.vitapetcare.config.root.Info;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
@Transactional
@RequiredArgsConstructor
@Slf4j
@Info(
  dev = Info.Dev.heltonOliveira,
  label = Info.Label.feature,
  date = "29/12/2025",
  description = "Serviço consolidado para gestão de serviços do catálogo"
)
public class CatalogService {

  private final ServiceRepository serviceRepository;

  public Optional<Service> save(Service service) {
    log.info("Salvando serviço: {}", service.getName());
    return Optional.of(serviceRepository.save(service));
  }

  public List<Service> findAll() {
    log.info("Buscando todos os serviços");
    return serviceRepository.findAll();
  }

  public Optional<Service> getById(Long id) {
    log.info("Buscando serviço por ID: {}", id);
    return serviceRepository.findById(id);
  }

  public void disable(Long id) {
    log.info("Desativando serviço ID: {}", id);
    serviceRepository.findById(id).ifPresent(service -> {
      service.disabled();
      serviceRepository.save(service);
    });
  }

  public void saveAllAndFlush(List<Service> services) {
    log.info("Salvando lista de serviços");
    services.forEach(this::save);
    serviceRepository.flush();
  }
}
