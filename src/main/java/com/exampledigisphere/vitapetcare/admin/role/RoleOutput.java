package com.exampledigisphere.vitapetcare.admin.role;

import com.exampledigisphere.vitapetcare.admin.permission.PermissionOutput;
import com.exampledigisphere.vitapetcare.auth.roles.ERole;

import java.util.Set;

public record RoleOutput(
  Long id,
  ERole name,
  Set<PermissionOutput> permissions,
  boolean active
) {
}
