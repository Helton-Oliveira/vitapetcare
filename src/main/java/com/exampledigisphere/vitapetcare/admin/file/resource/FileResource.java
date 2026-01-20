package com.exampledigisphere.vitapetcare.admin.file.resource;

import com.exampledigisphere.vitapetcare.admin.file.domain.File;
import com.exampledigisphere.vitapetcare.admin.file.useCases.DisableFileUseCase;
import com.exampledigisphere.vitapetcare.admin.file.useCases.FindAllFilesUseCase;
import com.exampledigisphere.vitapetcare.admin.file.useCases.FindFileByIdUseCase;
import com.exampledigisphere.vitapetcare.admin.file.useCases.SaveFileUseCase;
import com.exampledigisphere.vitapetcare.config.root.Info;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
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
@RequestMapping("/api/files")
@AllArgsConstructor
@Info(
  dev = Info.Dev.heltonOliveira,
  label = Info.Label.feature,
  date = "29/12/2025",
  description = "Recurso REST para gestão de arquivos"
)
public class FileResource {

  private final SaveFileUseCase saveFileUseCase;
  private final FindAllFilesUseCase findAllFilesUseCase;
  private final FindFileByIdUseCase findFileByIdUseCase;
  private final DisableFileUseCase disableFileUseCase;

  @PostMapping
  @PreAuthorize("hasAuthority('FILE_CREATE')")
  public ResponseEntity<File> create(@RequestBody @Valid File input, UriComponentsBuilder uriBuilder) {
    return saveFileUseCase.execute(input)
      .map(file -> {
        var uri = uriBuilder.path("/api/files/{id}").buildAndExpand(file.getId()).toUri();
        return ResponseEntity.created(uri).body(file);
      })
      .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
  }

  @PutMapping
  @PreAuthorize("hasAuthority('FILE_EDIT')")
  public ResponseEntity<File> update(@RequestBody @Valid File input) {
    return saveFileUseCase.execute(input)
      .map(ResponseEntity::ok)
      .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasAuthority('FILE_VIEW')")
  public ResponseEntity<File> getOne(@PathVariable Long id) {
    return findFileByIdUseCase.execute(id)
      .map(ResponseEntity::ok)
      .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping
  @PreAuthorize("hasAuthority('FILE_VIEW_LIST')")
  public ResponseEntity<Page<File>> getAll(@PageableDefault(size = 20) Pageable page) {
    return ResponseEntity.ok(findAllFilesUseCase.execute(page));
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasAuthority('FILE_DELETE')")
  public ResponseEntity<Void> disable(@PathVariable Long id) {
    disableFileUseCase.execute(id);
    return ResponseEntity.ok().build();
  }
}
