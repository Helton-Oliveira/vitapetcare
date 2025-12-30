package com.exampledigisphere.vitapetcare.admin.user.domain;

import com.exampledigisphere.vitapetcare.auth.roles.Role;

import java.util.Set;

public record UserOutput(
  Long id,
  String name,
  String email,
  Role role,
  boolean active,
  Set<String> authorities
) {
}
