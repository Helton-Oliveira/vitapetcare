package com.exampledigisphere.vitapetcare.admin.role;

import com.exampledigisphere.vitapetcare.auth.roles.ERole;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record RoleInput(
  Long id,
  @NotNull ERole name,
  Set<Long> permissionsIds
) {
}
