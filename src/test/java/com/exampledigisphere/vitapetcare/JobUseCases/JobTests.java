package com.exampledigisphere.vitapetcare.JobUseCases;

import com.exampledigisphere.vitapetcare.admin.status.Status;
import com.exampledigisphere.vitapetcare.auth.roles.Role;
import com.exampledigisphere.vitapetcare.catalog.job.Job;
import com.exampledigisphere.vitapetcare.catalog.job.JobRepository;
import com.exampledigisphere.vitapetcare.catalog.job.JobType;
import com.exampledigisphere.vitapetcare.catalog.job.useCases.CreateJob;
import com.exampledigisphere.vitapetcare.catalog.job.useCases.DisableJob;
import com.exampledigisphere.vitapetcare.catalog.job.useCases.GetAllJobs;
import com.exampledigisphere.vitapetcare.catalog.job.useCases.GetJobById;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
public class JobTests {

  @Autowired
  public JobRepository jobRepository;

  @Test
  @DisplayName("Deve criar um servico")
  public void createJobTest() {
    final var job = Job.create(
      "Tosa",
      BigDecimal.valueOf(180000),
      BigInteger.valueOf(2),
      Role.GROOMER,
      JobType.GROOMING,
      Status.ACTIVE,
      false,
      null
    );

    final var createJob = new CreateJob(jobRepository);

    createJob.execute(job)
      .ifPresent(savedJob -> {
        assertThat(savedJob.getId()).isNotNull();
        assertThat(savedJob.getName()).isEqualTo("Tosa");
      });
  }

  @Test
  @DisplayName("Deve buscar todos os jobs")
  public void geAllJobs() {
    final var getAllJobs = new GetAllJobs(jobRepository);
    final var pageable = PageRequest.of(0, 10);
    final Page<Job> jobs = getAllJobs.execute(pageable);

    assertThat(jobs.getTotalElements()).isEqualTo(1);
    assertThat(jobs.getTotalPages()).isEqualTo(1);
    assertThat(jobs.getNumber()).isEqualTo(0);
  }

  @Test
  @DisplayName("Deve buscar um job no banco de dados")
  public void getJob() {
    final var getJob = new GetJobById(jobRepository);
    getJob.execute(10L)
      .ifPresent(
        job -> {
          assertThat(job.getName()).isEqualTo("Tosa");
          assertThat(job.getTypesOfProfessional()).isEqualTo(Role.GROOMER);
          assertThat(job.getType()).isEqualTo(JobType.GROOMING);
          assertThat(job.getStatus()).isEqualTo(Status.ACTIVE);
        });
  }

  @Test
  @DisplayName("Deve realizar exclusao logica do Job")
  public void disableJob() {
    final var getJob = new GetJobById(jobRepository);
    final var activeJob = getJob.execute(10L)
      .filter(Job::isActive)
      .orElseThrow();

    activeJob.setEdited(true);

    final var disableJob = new DisableJob(jobRepository);
    final boolean isDisable = disableJob.execute(activeJob);

    assertThat(isDisable).isTrue();
  }

}
