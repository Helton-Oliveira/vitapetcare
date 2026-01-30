package com.exampledigisphere.vitapetcare.catalog.job.useCases;

import com.exampledigisphere.vitapetcare.catalog.job.Job;
import com.exampledigisphere.vitapetcare.catalog.job.JobRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional(readOnly = true)
public class GetAllJobs {

  private final JobRepository jobRepository;

  public Page<Job> execute(Pageable pageable) {
    log.info("GetAllJobs, {}", pageable);

    return jobRepository.findAll(pageable);
  }

}
