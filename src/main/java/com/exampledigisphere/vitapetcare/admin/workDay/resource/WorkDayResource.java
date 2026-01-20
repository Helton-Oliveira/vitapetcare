package com.exampledigisphere.vitapetcare.admin.workDay.resource;

import com.exampledigisphere.vitapetcare.admin.workDay.domain.WorkDay;
import com.exampledigisphere.vitapetcare.admin.workDay.useCases.DisableWorkDayUseCase;
import com.exampledigisphere.vitapetcare.admin.workDay.useCases.FindAllWorkDaysUseCase;
import com.exampledigisphere.vitapetcare.admin.workDay.useCases.FindWorkDayByIdUseCase;
import com.exampledigisphere.vitapetcare.admin.workDay.useCases.SaveWorkDayUseCase;
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
@RequestMapping("/api/works_days")
@RequiredArgsConstructor
@Info(
  dev = Info.Dev.heltonOliveira,
  label = Info.Label.feature,
  date = "29/12/2025",
  description = "Recurso REST para gestão de dias de trabalho"
)
public class WorkDayResource {

  private final SaveWorkDayUseCase saveWorkDayUseCase;
  private final FindAllWorkDaysUseCase findAllWorkDaysUseCase;
  private final FindWorkDayByIdUseCase findWorkDayByIdUseCase;
  private final DisableWorkDayUseCase disableWorkDayUseCase;

  @PostMapping
  @PreAuthorize("hasAuthority('WORK_DAY_CREATE')")
  public ResponseEntity<WorkDay> create(@RequestBody @Valid WorkDay input, UriComponentsBuilder uriBuilder) {
    return saveWorkDayUseCase.execute(input)
      .map(wk -> {
        var uri = uriBuilder.path("/api/works_days/{id}").buildAndExpand(wk.getId()).toUri();
        return ResponseEntity.created(uri).body(wk);
      })
      .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
  }

  @PutMapping
  @PreAuthorize("hasAuthority('WORK_DAY_EDIT')")
  public ResponseEntity<WorkDay> update(@RequestBody @Valid WorkDay input) {
    return saveWorkDayUseCase.execute(input)
      .map(ResponseEntity::ok)
      .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasAuthority('WORK_DAY_VIEW')")
  public ResponseEntity<WorkDay> getOne(@PathVariable Long id) {
    return findWorkDayByIdUseCase.execute(id)
      .map(ResponseEntity::ok)
      .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping
  @PreAuthorize("hasAuthority('WORK_DAY_VIEW_LIST')")
  public ResponseEntity<Page<WorkDay>> getAll(@PageableDefault(size = 20) Pageable page) {
    return ResponseEntity.ok(findAllWorkDaysUseCase.execute(page));
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasAuthority('WORK_DAY_DELETE')")
  public ResponseEntity<Void> disable(@PathVariable Long id) {
    disableWorkDayUseCase.execute(id);
    return ResponseEntity.ok().build();
  }
}
