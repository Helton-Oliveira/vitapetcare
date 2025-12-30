package com.exampledigisphere.vitapetcare.admin.workDay.resource;

import com.exampledigisphere.vitapetcare.admin.workDay.domain.WorkDayInput;
import com.exampledigisphere.vitapetcare.admin.workDay.domain.WorkDayOutput;
import com.exampledigisphere.vitapetcare.admin.workDay.service.WorkDayService;
import com.exampledigisphere.vitapetcare.config.root.Info;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/works_days")
@RequiredArgsConstructor
@Info(
  dev = Info.Dev.heltonOliveira,
  label = Info.Label.feature,
  date = "29/12/2025",
  description = "Recurso REST para gestão de dias de trabalho"
)
public class WorkDayResource {

  private final WorkDayService workDayService;

  @PostMapping
  @PreAuthorize("hasAuthority('WORK_DAY_CREATE')")
  public ResponseEntity<WorkDayOutput> create(@RequestBody @Valid WorkDayInput input, UriComponentsBuilder uriBuilder) {
    return workDayService.save(input)
      .map(wk -> {
        var uri = uriBuilder.path("/api/works_days/{id}").buildAndExpand(wk.id()).toUri();
        return ResponseEntity.created(uri).body(wk);
      })
      .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
  }

  @PutMapping
  @PreAuthorize("hasAuthority('WORK_DAY_EDIT')")
  public ResponseEntity<WorkDayOutput> update(@RequestBody @Valid WorkDayInput input) {
    return workDayService.save(input)
      .map(ResponseEntity::ok)
      .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasAuthority('WORK_DAY_VIEW')")
  public ResponseEntity<WorkDayOutput> getOne(@PathVariable Long id) {
    return workDayService.getById(id)
      .map(ResponseEntity::ok)
      .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping
  @PreAuthorize("hasAuthority('WORK_DAY_VIEW_LIST')")
  public ResponseEntity<List<WorkDayOutput>> getAll() {
    return ResponseEntity.ok(workDayService.findAll());
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasAuthority('WORK_DAY_DELETE')")
  public ResponseEntity<Void> disable(@PathVariable Long id) {
    workDayService.disable(id);
    return ResponseEntity.ok().build();
  }
}
