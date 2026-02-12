package com.exampledigisphere.vitapetcare.catalog.job;

import com.exampledigisphere.vitapetcare.admin.status.Status;
import com.exampledigisphere.vitapetcare.auth.roles.Role;
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
  private JobService jobService;

  @Test
  @DisplayName("Deve criar um servico")
  public void createJobTest() {
    final var job = Job.create(
      "Tosa",
      BigDecimal.valueOf(180),
      BigInteger.valueOf(2),
      Role.GROOMER,
      JobType.GROOMING,
      Status.ACTIVE,
      false,
      null
    );
    job.setEdited(true);

    jobService.catalog(job)
      .ifPresent(savedJob -> {
        assertThat(savedJob.getId()).isNotNull();
        assertThat(savedJob.getName()).isEqualTo("Tosa");
        // Verifica se a conversão ocorreu (180 * 100 = 18000)
        assertThat(savedJob.getValueInCents()).isEqualByComparingTo(BigDecimal.valueOf(18000));
      });
  }

  @Test
  @DisplayName("Deve buscar todos os jobs")
  public void geAllJobs() {
    final var pageable = PageRequest.of(0, 10);
    final Page<Job> jobs = jobService.browse(pageable);

    assertThat(jobs).isNotNull();
  }

  @Test
  @DisplayName("Deve buscar um job no banco de dados")
  public void getJob() {
    final var job = Job.create(
      "Tosa",
      BigDecimal.valueOf(180),
      BigInteger.valueOf(2),
      Role.GROOMER,
      JobType.GROOMING,
      Status.ACTIVE,
      false,
      null
    );
    job.setEdited(true);
    final var saved = jobService.catalog(job).orElseThrow();

    jobService.retrieve(saved.getId())
      .ifPresent(
        retrievedJob -> {
          assertThat(retrievedJob.getName()).isEqualTo("Tosa");
          assertThat(retrievedJob.getTypesOfProfessional()).isEqualTo(Role.GROOMER);
          assertThat(retrievedJob.getType()).isEqualTo(JobType.GROOMING);
          assertThat(retrievedJob.getStatus()).isEqualTo(Status.ACTIVE);
          // retrieve converte de volta (18000 / 100 = 180)
          assertThat(retrievedJob.getValueInCents()).isEqualByComparingTo(BigDecimal.valueOf(180));
        });
  }

  @Test
  @DisplayName("Deve realizar exclusao logica do Job")
  public void disableJob() {
    final var job = Job.create(
      "Tosa",
      BigDecimal.valueOf(180),
      BigInteger.valueOf(2),
      Role.GROOMER,
      JobType.GROOMING,
      Status.ACTIVE,
      false,
      null
    );
    job.setEdited(true);
    final var saved = jobService.catalog(job).orElseThrow();

    final boolean isDisable = jobService.discontinue(saved.getId());

    assertThat(isDisable).isTrue();
    assertThat(jobService.retrieve(saved.getId())).isEmpty();
  }

}
