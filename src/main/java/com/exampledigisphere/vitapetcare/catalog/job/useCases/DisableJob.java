package com.exampledigisphere.vitapetcare.catalog.job.useCases;

import com.exampledigisphere.vitapetcare.catalog.job.Job;
import com.exampledigisphere.vitapetcare.catalog.job.JobRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class DisableJob {
  final JobRepository jobRepository;

  public Boolean execute(@NonNull Long id) {
    log.info("DisableJob, {}", id);

    return jobRepository.findById(id)
      .filter(Job::isActive)
      .map(job -> {
        job.setActive(false);
        return job;
      })
      .map(jobRepository::save)
      .map(Job::isActive)
      .orElse(false);
  }
}
