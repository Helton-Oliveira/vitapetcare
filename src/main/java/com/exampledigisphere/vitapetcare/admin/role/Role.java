package com.exampledigisphere.vitapetcare.admin.role;

import com.exampledigisphere.vitapetcare.admin.permission.Permission;
import com.exampledigisphere.vitapetcare.auth.roles.ERole;
import com.exampledigisphere.vitapetcare.config.root.BaseEntity;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "rol_roles")
public class Role extends BaseEntity {

  public interface Json {
    interface List {
    }

    interface Detail extends Permission.Json.List {
    }

    interface All extends Permission.Json.Detail {
    }
  }

  @NotNull
  @Enumerated(EnumType.STRING)
  @JsonView(Json.List.class)
  private ERole name;

  @NotNull
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
    name = "roles_permissions",
    joinColumns = @JoinColumn(name = "role_id"),
    inverseJoinColumns = @JoinColumn(name = "permission_id"))
  @JsonView(Json.Detail.class)
  Set<Permission> permissions = new HashSet();

  public ERole getName() {
    return name;
  }

  public void setName(ERole name) {
    this.name = name;
  }

  public Set<Permission> getPermissions() {
    return permissions;
  }

  public void setPermissions(Set<Permission> permissions) {
    this.permissions = permissions;
  }
}
