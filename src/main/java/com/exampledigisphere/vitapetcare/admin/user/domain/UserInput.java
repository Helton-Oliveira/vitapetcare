package com.exampledigisphere.vitapetcare.admin.user.domain;

import com.exampledigisphere.vitapetcare.admin.file.domain.File;
import com.exampledigisphere.vitapetcare.auth.roles.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record UserInput(
  Long id,
  @NotBlank String name,
  @NotBlank String email,
  String password,
  @NotNull Role role,
  Set<File> files,
  boolean active
) {
}
