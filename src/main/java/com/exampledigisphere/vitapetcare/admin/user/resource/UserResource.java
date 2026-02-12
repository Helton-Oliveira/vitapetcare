package com.exampledigisphere.vitapetcare.admin.user.resource;

import com.exampledigisphere.vitapetcare.admin.user.UserDTO;
import com.exampledigisphere.vitapetcare.admin.user.UserService;
import com.exampledigisphere.vitapetcare.admin.user.domain.UserAssociations;
import com.exampledigisphere.vitapetcare.config.root.Info;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.Set;

@RestController
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/api/users")
@Info(
  dev = Info.Dev.heltonOliveira,
  label = Info.Label.feature,
  date = "06/02/2026",
  description = "Recurso REST para gestão de usuários"
)
public class UserResource {

  private final UserService userService;

  public UserResource(final UserService userService) {
    this.userService = userService;
  }

  @PostMapping
  @PreAuthorize("hasAuthority('USER_CREATE')")
  @Transactional
  public ResponseEntity<UserDTO> create(@RequestBody @Valid UserDTO input, UriComponentsBuilder uriBuilder) {
    return userService.register(input, Collections.emptySet())
      .map(usr -> {
        var uri = uriBuilder.path("/api/users/{id}").buildAndExpand(usr.getId()).toUri();
        return ResponseEntity.created(uri).body(usr);
      })
      .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
  }

  @PutMapping
  @PreAuthorize("hasAuthority('USER_EDIT')")
  @Transactional
  public ResponseEntity<UserDTO> update(@RequestBody @Valid UserDTO input) {
    return userService.register(input, Collections.emptySet())
      .map(ResponseEntity::ok)
      .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasAuthority('USER_VIEW')")
  public ResponseEntity<UserDTO> getOne(@PathVariable Long id) {
    return userService.retrieve(id, Set.of(UserAssociations.FILES, UserAssociations.WORK_DAYS))
      .map(ResponseEntity::ok)
      .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping
  @PreAuthorize("hasAuthority('USER_VIEW_LIST')")
  public ResponseEntity<Page<UserDTO>> getAll(@PageableDefault(page = 0, size = 20) Pageable pageable) {
    return ResponseEntity.ok(userService.list(pageable));
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasAuthority('USER_DELETE')")
  public ResponseEntity<?> disable(@PathVariable Long id) {
    userService.suspend(id);
    return ResponseEntity.ok().build();
  }
}
