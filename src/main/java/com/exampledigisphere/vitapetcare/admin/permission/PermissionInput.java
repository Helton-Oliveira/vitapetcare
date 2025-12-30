package com.exampledigisphere.vitapetcare.admin.permission;

import jakarta.validation.constraints.NotBlank;

public record PermissionInput(
  Long id,
  @NotBlank String name
) {
}
