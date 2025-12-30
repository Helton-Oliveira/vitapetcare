package com.exampledigisphere.vitapetcare.catalog.serviceType.service;

import com.exampledigisphere.vitapetcare.catalog.serviceType.domain.ServiceType;
import com.exampledigisphere.vitapetcare.catalog.serviceType.repository.ServiceTypeRepository;
import com.exampledigisphere.vitapetcare.config.root.Info;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
@Info(
  dev = Info.Dev.heltonOliveira,
  label = Info.Label.feature,
  date = "29/12/2025",
  description = "Serviço consolidado para gestão de tipos de serviço"
)
public class ServiceTypeService {

  private final ServiceTypeRepository serviceTypeRepository;

  public Optional<ServiceType> save(ServiceType serviceType) {
    log.info("Salvando tipo de serviço: {}", serviceType.getName());
    return Optional.of(serviceTypeRepository.save(serviceType));
  }

  public List<ServiceType> findAll() {
    log.info("Buscando todos os tipos de serviço");
    return serviceTypeRepository.findAll();
  }

  public Optional<ServiceType> getById(Long id) {
    log.info("Buscando tipo de serviço por ID: {}", id);
    return serviceTypeRepository.findById(id);
  }

  public void disable(Long id) {
    log.info("Desativando tipo de serviço ID: {}", id);
    serviceTypeRepository.findById(id).ifPresent(serviceType -> {
      serviceType.disabled();
      serviceTypeRepository.save(serviceType);
    });
  }

  public void saveAllAndFlush(List<ServiceType> serviceTypes) {
    log.info("Salvando lista de tipos de serviço");
    serviceTypes.forEach(this::save);
    serviceTypeRepository.flush();
  }
}
