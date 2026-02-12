package com.exampledigisphere.vitapetcare.admin.workDay.resource;

import com.exampledigisphere.vitapetcare.admin.workDay.WorkDayAssociations;
import com.exampledigisphere.vitapetcare.admin.workDay.WorkDayDTO;
import com.exampledigisphere.vitapetcare.admin.workDay.WorkDayService;
import com.exampledigisphere.vitapetcare.config.root.Info;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.Set;

@RestController
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/api/works_days")
@Info(
  dev = Info.Dev.heltonOliveira,
  label = Info.Label.feature,
  date = "06/02/2026",
  description = "Recurso REST para gestão de dias de trabalho"
)
public class WorkDayResource {

  private final WorkDayService workDayService;

  public WorkDayResource(final WorkDayService workDayService) {
    this.workDayService = workDayService;
  }

  @PostMapping
  @PreAuthorize("hasAuthority('WORK_DAY_CREATE')")
  public ResponseEntity<WorkDayDTO> create(@RequestBody @Valid WorkDayDTO input, UriComponentsBuilder uriBuilder) {
    return workDayService.schedule(input, Collections.emptySet())
      .map(wk -> {
        var uri = uriBuilder.path("/api/works_days/{id}").buildAndExpand(wk.getId()).toUri();
        return ResponseEntity.created(uri).body(wk);
      })
      .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
  }

  @PutMapping
  @PreAuthorize("hasAuthority('WORK_DAY_EDIT')")
  public ResponseEntity<WorkDayDTO> update(@RequestBody @Valid WorkDayDTO input) {
    return workDayService.schedule(input, Collections.emptySet())
      .map(ResponseEntity::ok)
      .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasAuthority('WORK_DAY_VIEW')")
  public ResponseEntity<WorkDayDTO> getOne(@PathVariable Long id) {
    return workDayService.retrieve(id, Set.of(WorkDayAssociations.USER, WorkDayAssociations.TIME_PERIOD))
      .map(ResponseEntity::ok)
      .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping
  @PreAuthorize("hasAuthority('WORK_DAY_VIEW_LIST')")
  public ResponseEntity<Page<WorkDayDTO>> getAll(@PageableDefault(size = 20) Pageable page) {
    return ResponseEntity.ok(workDayService.list(page));
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasAuthority('WORK_DAY_DELETE')")
  public ResponseEntity<Void> disable(@PathVariable Long id) {
    workDayService.suspend(id);
    return ResponseEntity.ok().build();
  }
}
