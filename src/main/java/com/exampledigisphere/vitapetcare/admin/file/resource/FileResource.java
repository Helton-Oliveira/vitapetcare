package com.exampledigisphere.vitapetcare.admin.file.resource;

import com.exampledigisphere.vitapetcare.admin.file.domain.FileInput;
import com.exampledigisphere.vitapetcare.admin.file.domain.FileOutput;
import com.exampledigisphere.vitapetcare.admin.file.service.FileService;
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
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/api/files")
@RequiredArgsConstructor
@Info(
  dev = Info.Dev.heltonOliveira,
  label = Info.Label.feature,
  date = "29/12/2025",
  description = "Recurso REST para gestão de arquivos"
)
public class FileResource {

  private final FileService fileService;

  @PostMapping
  @PreAuthorize("hasAuthority('FILE_CREATE')")
  public ResponseEntity<FileOutput> create(@RequestBody @Valid FileInput input, UriComponentsBuilder uriBuilder) {
    return fileService.save(input)
      .map(file -> {
        var uri = uriBuilder.path("/api/files/{id}").buildAndExpand(file.id()).toUri();
        return ResponseEntity.created(uri).body(file);
      })
      .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
  }

  @PutMapping
  @PreAuthorize("hasAuthority('FILE_EDIT')")
  public ResponseEntity<FileOutput> update(@RequestBody @Valid FileInput input) {
    return fileService.update(input)
      .map(ResponseEntity::ok)
      .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasAuthority('FILE_VIEW')")
  public ResponseEntity<FileOutput> getOne(@PathVariable Long id) {
    return fileService.findById(id)
      .map(ResponseEntity::ok)
      .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping
  @PreAuthorize("hasAuthority('FILE_VIEW_LIST')")
  public ResponseEntity<List<FileOutput>> getAll() {
    return ResponseEntity.ok(fileService.findAll());
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasAuthority('FILE_DELETE')")
  public ResponseEntity<Void> disable(@PathVariable Long id) {
    fileService.disable(id);
    return ResponseEntity.ok().build();
  }
}
