package com.exampledigisphere.vitapetcare.admin.role;

import com.exampledigisphere.vitapetcare.admin.permission.PermissionMapper;
import com.exampledigisphere.vitapetcare.config.root.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RoleMapper implements Mapper<RoleInput, Role, RoleOutput> {

  private final PermissionMapper permissionMapper;

  @Override
  public Role toDomain(RoleInput input) {
    if (input == null) {
      return null;
    }
    Role role = new Role();
    role.setId(input.id());
    role.setName(input.name());
    return role;
  }

  @Override
  public RoleOutput toOutput(Role domain) {
    if (domain == null) {
      return null;
    }
    return new RoleOutput(
      domain.getId(),
      domain.getName(),
      domain.getPermissions() != null ? domain.getPermissions().stream()
        .map(permissionMapper::toOutput)
        .collect(Collectors.toSet()) : null,
      domain.isActive()
    );
  }
}
