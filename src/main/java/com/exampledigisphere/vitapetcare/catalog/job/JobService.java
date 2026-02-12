package com.exampledigisphere.vitapetcare.catalog.job;

import com.exampledigisphere.vitapetcare.config.root.Info;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional
@Info(
  dev = Info.Dev.heltonOliveira,
  label = Info.Label.feature,
  date = "06/02/2026",
  description = "Serviço para gestão semântica do catálogo de serviços (Jobs)"
)
public class JobService {

  private final JobRepository jobRepository;

  public JobService(final JobRepository jobRepository) {
    this.jobRepository = jobRepository;
  }

  @Info(
    dev = Info.Dev.heltonOliveira,
    label = Info.Label.feature,
    date = "06/02/2026",
    description = "Cataloga um novo serviço ou atualiza um existente processando valores monetários e temporais"
  )
  public Optional<Job> catalog(@NonNull Job input) {
    log.info("Catalogando serviço: {}", input);

    return Optional.of(input)
      .filter(Job::isEdited)
      .map(job -> {
        job.convertDollarsInCents();
        job.convertHoursInMinutes();
        return job;
      })
      .map(jobRepository::save);
  }

  @Info(
    dev = Info.Dev.heltonOliveira,
    label = Info.Label.feature,
    date = "06/02/2026",
    description = "Recupera um serviço específico através de seu identificador com conversão de valores"
  )
  @Transactional(readOnly = true)
  public Optional<Job> retrieve(@NonNull Long jobId) {
    log.info("Recuperando serviço ID: {}", jobId);

    return jobRepository.findById(jobId)
      .map(job -> {
        job.convertCentsInDollars();
        job.convertMinutesInHours();
        return job;
      });
  }

  @Info(
    dev = Info.Dev.heltonOliveira,
    label = Info.Label.feature,
    date = "06/02/2026",
    description = "Explora o catálogo de serviços ativos de forma paginada"
  )
  @Transactional(readOnly = true)
  public Page<Job> browse(Pageable pageable) {
    log.info("Explorando catálogo de serviços");

    return jobRepository.findAll(pageable)
      .map(job -> {
        job.convertCentsInDollars();
        job.convertMinutesInHours();
        return job;
      });
  }

  @Info(
    dev = Info.Dev.heltonOliveira,
    label = Info.Label.feature,
    date = "06/02/2026",
    description = "Descontinua a oferta de um serviço no catálogo"
  )
  public Boolean discontinue(@NonNull Long id) {
    log.info("Descontinuando serviço ID: {}", id);

    return jobRepository.findById(id)
      .filter(Job::isActive)
      .map(job -> {
        job.setActive(false);
        return job;
      })
      .map(jobRepository::save)
      .map(Job::isActive)
      .map(active -> !active)
      .orElse(false);
  }
}
