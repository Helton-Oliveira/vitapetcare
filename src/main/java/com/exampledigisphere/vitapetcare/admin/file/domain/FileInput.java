package com.exampledigisphere.vitapetcare.admin.file.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FileInput(
  Long id,
  @NotBlank String name,
  @NotBlank String path,
  @NotNull FileType type,
  Long userId
) {
}
