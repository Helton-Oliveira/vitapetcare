package com.exampledigisphere.vitapetcare.admin.timePeriod.resource;

import com.exampledigisphere.vitapetcare.admin.timePeriod.TimePeriodAssociations;
import com.exampledigisphere.vitapetcare.admin.timePeriod.TimePeriodDTO;
import com.exampledigisphere.vitapetcare.admin.timePeriod.TimePeriodService;
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

import java.util.Set;

import static java.util.Collections.emptySet;

@RestController
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/api/times-periods")
@Info(
  dev = Info.Dev.heltonOliveira,
  label = Info.Label.feature,
  date = "06/02/2026",
  description = "Recurso REST para gestão de períodos de tempo"
)
public class TimePeriodResource {

  private final TimePeriodService timePeriodService;

  public TimePeriodResource(final TimePeriodService timePeriodService) {
    this.timePeriodService = timePeriodService;
  }

  @PostMapping
  @PreAuthorize("hasAuthority('TIME_PERIOD_CREATE')")
  public ResponseEntity<TimePeriodDTO> create(@RequestBody @Valid TimePeriodDTO input, UriComponentsBuilder uriBuilder) {
    return timePeriodService.define(input, emptySet())
      .map(wk -> {
        var uri = uriBuilder.path("/api/times-periods/{id}").buildAndExpand(wk.getId()).toUri();
        return ResponseEntity.created(uri).body(wk);
      })
      .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
  }

  @PutMapping
  @PreAuthorize("hasAuthority('TIME_PERIOD_EDIT')")
  public ResponseEntity<TimePeriodDTO> update(@RequestBody @Valid TimePeriodDTO input) {
    return timePeriodService.define(input, emptySet())
      .map(ResponseEntity::ok)
      .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasAuthority('TIME_PERIOD_VIEW')")
  public ResponseEntity<TimePeriodDTO> getOne(@PathVariable Long id) {
    return timePeriodService.retrieve(id, Set.of(TimePeriodAssociations.WORK_DAYS))
      .map(ResponseEntity::ok)
      .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping
  @PreAuthorize("hasAuthority('TIME_PERIOD_VIEW_LIST')")
  public ResponseEntity<Page<TimePeriodDTO>> getAll(@PageableDefault(size = 20) Pageable page) {
    return ResponseEntity.ok(timePeriodService.list(page));
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasAuthority('TIME_PERIOD_DELETE')")
  public ResponseEntity<Void> disable(@PathVariable Long id) {
    timePeriodService.discard(id);
    return ResponseEntity.ok().build();
  }
}
