package com.exampledigisphere.vitapetcare.admin.timePeriod.resource;

import com.exampledigisphere.vitapetcare.admin.timePeriod.domain.TimePeriod;
import com.exampledigisphere.vitapetcare.admin.timePeriod.useCases.DisableTimePeriodUseCase;
import com.exampledigisphere.vitapetcare.admin.timePeriod.useCases.FindAllTimePeriodsUseCase;
import com.exampledigisphere.vitapetcare.admin.timePeriod.useCases.FindTimePeriodByIdUseCase;
import com.exampledigisphere.vitapetcare.admin.timePeriod.useCases.SaveTimePeriodUseCase;
import com.exampledigisphere.vitapetcare.config.root.Info;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/api/times-periods")
@RequiredArgsConstructor
@Info(
  dev = Info.Dev.heltonOliveira,
  label = Info.Label.feature,
  date = "29/12/2025",
  description = "Recurso REST para gestão de dias de trabalho"
)
public class TimePeriodResource {

  private final SaveTimePeriodUseCase saveTimePeriodUseCase;
  private final FindAllTimePeriodsUseCase findAllTimePeriodsUseCase;
  private final FindTimePeriodByIdUseCase findTimePeriodByIdUseCase;
  private final DisableTimePeriodUseCase disableTimePeriodUseCase;

  @PostMapping
  @PreAuthorize("hasAuthority('TIME_PERIOD_CREATE')")
  public ResponseEntity<TimePeriod> create(@RequestBody @Valid TimePeriod input, UriComponentsBuilder uriBuilder) {
    return saveTimePeriodUseCase.execute(input)
      .map(wk -> {
        var uri = uriBuilder.path("/api/times-periods/{id}").buildAndExpand(wk.getId()).toUri();
        return ResponseEntity.created(uri).body(wk);
      })
      .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
  }

  @PutMapping
  @PreAuthorize("hasAuthority('TIME_PERIOD_EDIT')")
  public ResponseEntity<TimePeriod> update(@RequestBody @Valid TimePeriod input) {
    return saveTimePeriodUseCase.execute(input)
      .map(ResponseEntity::ok)
      .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasAuthority('TIME_PERIOD_VIEW')")
  public ResponseEntity<TimePeriod> getOne(@PathVariable Long id) {
    return findTimePeriodByIdUseCase.execute(id)
      .map(ResponseEntity::ok)
      .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping
  @PreAuthorize("hasAuthority('TIME_PERIOD_VIEW_LIST')")
  public ResponseEntity<Page<TimePeriod>> getAll(@PageableDefault(size = 20) Pageable page) {
    return ResponseEntity.ok(findAllTimePeriodsUseCase.execute(page));
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasAuthority('TIME_PERIOD_DELETE')")
  public ResponseEntity<Void> disable(@PathVariable Long id) {
    disableTimePeriodUseCase.execute(id);
    return ResponseEntity.ok().build();
  }
}
