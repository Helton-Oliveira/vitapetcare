package com.exampledigisphere.vitapetcare.admin.user.domain;

import com.exampledigisphere.vitapetcare.admin.file.domain.File;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public record UserInput(
  Long id,
  @NotBlank String name,
  @NotBlank String email,
  String password,
  Set<Long> rolesIds,
  Set<File> files,
  boolean active
) {
}
