package com.exampledigisphere.vitapetcare.admin.user.domain;

import java.util.Set;

public record UserOutput(
  Long id,
  String name,
  String email,
  Set<String> roles,
  boolean active
) {
}
