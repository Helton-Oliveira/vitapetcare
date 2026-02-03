package com.exampledigisphere.vitapetcare.catalog.job.useCases;

import com.exampledigisphere.vitapetcare.catalog.job.Job;
import com.exampledigisphere.vitapetcare.catalog.job.JobRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class CreateJob {

  private final JobRepository jobRepository;

  public Optional<Job> execute(@NonNull Job input) {
    log.info("CreateJob, {}", input);

    return Optional.of(input)
      .filter(Job::isEdited)
      .map(job -> {
        job.convertDollarsInCents();
        job.convertHoursInMinutes();
        return job;
      })
      .map(jobRepository::save);
  }

}
