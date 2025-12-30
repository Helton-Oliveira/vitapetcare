package com.exampledigisphere.vitapetcare.admin.permission;

import com.exampledigisphere.vitapetcare.config.root.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class PermissionMapper implements Mapper<PermissionInput, Permission, PermissionOutput> {

  @Override
  public Permission toDomain(PermissionInput input) {
    if (input == null) {
      return null;
    }
    Permission permission = new Permission();
    permission.setId(input.id());
    permission.setName(input.name());
    return permission;
  }

  @Override
  public PermissionOutput toOutput(Permission domain) {
    if (domain == null) {
      return null;
    }
    return new PermissionOutput(
      domain.getId(),
      domain.getName(),
      domain.isActive()
    );
  }
}
