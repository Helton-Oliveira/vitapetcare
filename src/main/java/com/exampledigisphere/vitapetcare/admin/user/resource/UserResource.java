package com.exampledigisphere.vitapetcare.admin.user.resource;

import com.exampledigisphere.vitapetcare.admin.user.domain.UserInput;
import com.exampledigisphere.vitapetcare.admin.user.domain.UserOutput;
import com.exampledigisphere.vitapetcare.admin.user.service.UserService;
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
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Info(
  dev = Info.Dev.heltonOliveira,
  label = Info.Label.feature,
  date = "29/12/2025",
  description = "Recurso REST para gestão de usuários"
)
public class UserResource {

  private final UserService userService;

  @PostMapping
  @PreAuthorize("hasAuthority('USER_CREATE')")
  public ResponseEntity<UserOutput> create(@RequestBody @Valid UserInput input, UriComponentsBuilder uriBuilder) {
    return userService.save(input)
      .map(usr -> {
        var uri = uriBuilder.path("/api/users/{id}").buildAndExpand(usr.id()).toUri();
        return ResponseEntity.created(uri).body(usr);
      })
      .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
  }

  @PutMapping
  @PreAuthorize("hasAuthority('USER_EDIT')")
  public ResponseEntity<UserOutput> update(@RequestBody @Valid UserInput input) {
    return userService.update(input)
      .map(ResponseEntity::ok)
      .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasAuthority('USER_VIEW')")
  public ResponseEntity<UserOutput> getOne(@PathVariable Long id) {
    return userService.findById(id)
      .map(ResponseEntity::ok)
      .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping
  @PreAuthorize("hasAuthority('USER_VIEW_LIST')")
  public ResponseEntity<Page<UserOutput>> getAll(@PageableDefault(page = 0, size = 20) Pageable pageable) {
    return ResponseEntity.ok(userService.findAll(pageable));
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasAuthority('USER_DELETE')")
  public ResponseEntity<Void> disable(@PathVariable Long id) {
    userService.disable(id);
    return ResponseEntity.ok().build();
  }
}
