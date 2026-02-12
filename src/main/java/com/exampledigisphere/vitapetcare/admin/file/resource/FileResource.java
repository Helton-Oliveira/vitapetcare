package com.exampledigisphere.vitapetcare.admin.file.resource;

import com.exampledigisphere.vitapetcare.admin.file.FileAssociations;
import com.exampledigisphere.vitapetcare.admin.file.FileDTO;
import com.exampledigisphere.vitapetcare.admin.file.FileService;
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
@RequestMapping("/api/files")
@Info(
  dev = Info.Dev.heltonOliveira,
  label = Info.Label.feature,
  date = "06/02/2026",
  description = "Recurso REST para gestão de arquivos"
)
public class FileResource {

  private final FileService fileService;

  public FileResource(FileService fileService) {
    this.fileService = fileService;
  }

  @PostMapping
  @PreAuthorize("hasAuthority('FILE_CREATE')")
  public ResponseEntity<FileDTO> create(@RequestBody @Valid FileDTO input, UriComponentsBuilder uriBuilder) {
    return fileService.store(input, Collections.emptySet())
      .map(file -> {
        var uri = uriBuilder.path("/api/files/{id}").buildAndExpand(file.getId()).toUri();
        return ResponseEntity.created(uri).body(file);
      })
      .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
  }

  @PutMapping
  @PreAuthorize("hasAuthority('FILE_EDIT')")
  public ResponseEntity<FileDTO> update(@RequestBody @Valid FileDTO input) {
    return fileService.store(input, Collections.emptySet())
      .map(ResponseEntity::ok)
      .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasAuthority('FILE_VIEW')")
  public ResponseEntity<FileDTO> getOne(@PathVariable Long id) {
    return fileService.retrieve(id, Set.of(FileAssociations.USER))
      .map(ResponseEntity::ok)
      .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping
  @PreAuthorize("hasAuthority('FILE_VIEW_LIST')")
  public ResponseEntity<Page<FileDTO>> getAll(@PageableDefault(size = 20) Pageable page) {
    return ResponseEntity.ok(fileService.list(page));
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasAuthority('FILE_DELETE')")
  public ResponseEntity<Void> disable(@PathVariable Long id) {
    fileService.discard(id);
    return ResponseEntity.ok().build();
  }
}
