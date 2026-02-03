package com.exampledigisphere.vitapetcare.catalog.job;

import com.exampledigisphere.vitapetcare.catalog.job.useCases.CreateJob;
import com.exampledigisphere.vitapetcare.catalog.job.useCases.DisableJob;
import com.exampledigisphere.vitapetcare.catalog.job.useCases.GetAllJobs;
import com.exampledigisphere.vitapetcare.catalog.job.useCases.GetJobById;
import com.exampledigisphere.vitapetcare.config.root.Info;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
@Info(
  dev = Info.Dev.heltonOliveira,
  label = Info.Label.feature,
  date = "29/12/2025",
  description = "Recurso REST para gestão de servicos"
)
public class JobResource {

  private final CreateJob createJob;
  private final GetAllJobs getAllJobs;
  private final GetJobById getJobById;
  private final DisableJob disableJob;

  @PostMapping
  @PreAuthorize("hasAuthority('JOB_CREATE')")
  @Transactional
  public ResponseEntity<?> create(@RequestBody @Valid Job input, UriComponentsBuilder uriBuilder) {
    return createJob.execute(input)
      .map(job -> {
        var uri = uriBuilder.path("/api/jobs/{id}").buildAndExpand(job.getId()).toUri();
        return ResponseEntity.created(uri).body(job);
      })
      .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
  }

  @PutMapping
  @PreAuthorize("hasAuthority('JOB_EDIT')")
  @Transactional
  public ResponseEntity<?> update(@RequestBody @Valid Job input) {
    return createJob.execute(input)
      .map(ResponseEntity::ok)
      .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasAuthority('JOB_VIEW')")
  public ResponseEntity<?> getOne(@PathVariable Long id) {
    return getJobById.execute(id)
      .map(ResponseEntity::ok)
      .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping
  @PreAuthorize("hasAuthority('JOB_VIEW_LIST')")
  public ResponseEntity<Page<?>> getAll(@PageableDefault(page = 0, size = 20) Pageable pageable) {
    return ResponseEntity.ok(getAllJobs.execute(pageable));
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasAuthority('JOB_DELETE')")
  public ResponseEntity<?> disable(@PathVariable Long id) {
    disableJob.execute(id);
    return ResponseEntity.ok().build();
  }
}
