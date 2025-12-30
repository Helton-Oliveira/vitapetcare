package com.exampledigisphere.vitapetcare.admin.file.domain;

import com.exampledigisphere.vitapetcare.admin.user.domain.UserInput;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FileInput(
  Long id,
  @NotBlank String name,
  @NotBlank String path,
  @NotNull FileType type,
  @NotNull boolean _edited,
  @NotNull boolean active,
  UserInput user
) {
}
