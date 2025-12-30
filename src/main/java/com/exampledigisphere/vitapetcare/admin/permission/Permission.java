package com.exampledigisphere.vitapetcare.admin.permission;

import com.exampledigisphere.vitapetcare.config.root.BaseEntity;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "per_permissions")
public class Permission extends BaseEntity {

  public interface Json {
    interface List {
    }

    interface Detail extends List {
    }

    interface All extends Detail {
    }
  }

  @NotBlank
  @JsonView(Json.List.class)
  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
