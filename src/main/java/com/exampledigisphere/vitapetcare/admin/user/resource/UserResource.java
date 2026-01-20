package com.exampledigisphere.vitapetcare.admin.user.resource;

import com.exampledigisphere.vitapetcare.admin.user.domain.User;
import com.exampledigisphere.vitapetcare.admin.user.useCases.DisableUserUseCase;
import com.exampledigisphere.vitapetcare.admin.user.useCases.FindAllUsersUseCase;
import com.exampledigisphere.vitapetcare.admin.user.useCases.FindUserByIdUseCase;
import com.exampledigisphere.vitapetcare.admin.user.useCases.SaveUserUseCase;
import com.exampledigisphere.vitapetcare.config.root.Info;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
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

  private final SaveUserUseCase saveUserUseCase;
  private final FindAllUsersUseCase findAllUsersUseCase;
  private final FindUserByIdUseCase findUserByIdUseCase;
  private final DisableUserUseCase disableUserUseCase;

  @PostMapping
  @PreAuthorize("hasAuthority('USER_CREATE')")
  @Transactional
  public ResponseEntity<?> create(@RequestBody @Valid User input, UriComponentsBuilder uriBuilder) {
    return saveUserUseCase.execute(input)
      .map(usr -> {
        var uri = uriBuilder.path("/api/users/{id}").buildAndExpand(usr.getId()).toUri();
        return ResponseEntity.created(uri).body(usr);
      })
      .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
  }

  @PutMapping
  @PreAuthorize("hasAuthority('USER_EDIT')")
  @Transactional
  public ResponseEntity<?> update(@RequestBody @Valid User input) {
    return saveUserUseCase.execute(input)
      .map(ResponseEntity::ok)
      .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasAuthority('USER_VIEW')")
  public ResponseEntity<?> getOne(@PathVariable Long id) {
    return findUserByIdUseCase.execute(id)
      .map(User::loadFiles)
      .map(ResponseEntity::ok)
      .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping
  @PreAuthorize("hasAuthority('USER_VIEW_LIST')")
  public ResponseEntity<Page<?>> getAll(@PageableDefault(page = 0, size = 20) Pageable pageable) {
    return ResponseEntity.ok(findAllUsersUseCase.execute(pageable));
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasAuthority('USER_DELETE')")
  public ResponseEntity<?> disable(@PathVariable Long id) {
    disableUserUseCase.execute(id);
    return ResponseEntity.ok().build();
  }
}
