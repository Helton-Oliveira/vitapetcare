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
@Transactional(readOnly = true)
public class GetJobById {

  private final JobRepository jobRepository;

  public Optional<Job> execute(@NonNull Long jobId) {
    log.info("GetJobById, {}", jobId);

    return jobRepository.findById(jobId)
      .map(job -> {
        job.convertCentsInDollars();
        job.convertMinutesInHours();
        return job;
      });
  }

}
