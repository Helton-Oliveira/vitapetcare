package com.exampledigisphere.vitapetcare.admin.user.domain;

import com.exampledigisphere.vitapetcare.admin.file.domain.File;
import com.exampledigisphere.vitapetcare.admin.workDay.domain.WorkDay;
import com.exampledigisphere.vitapetcare.auth.roles.Role;
import com.exampledigisphere.vitapetcare.config.root.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "usr_users")
@NoArgsConstructor
public class User extends BaseEntity {

  @NotBlank
  @JsonView(Json.List.class)
  private String name;

  @NotBlank
  @JsonView(Json.List.class)
  private String email;

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String password;

  @Enumerated(EnumType.STRING)
  private Role role;

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
  @JsonIgnoreProperties("user")
  @JsonView(Json.WithFIle.class)
  private Set<File> files = new HashSet();

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
  @JsonIgnoreProperties("user")
  @JsonView(Json.WithFIle.class)
  private Set<WorkDay> workDays = new HashSet();

  public User loadFiles() {
    Hibernate.initialize(files);
    return this;
  }

  public User loadWorkDay() {
    Hibernate.initialize(workDays);
    return this;
  }

  public interface Json {
    interface List {
    }

    interface Detail extends List {
    }

    interface WithFIle extends File.Json.List {
    }

    interface WithWorkDay extends WorkDay.Json.List {
    }

    interface All extends Detail, WithFIle, WithWorkDay {
    }
  }

  public interface Authority {
    String USER_CREATE = "USER_CREATE";
    String USER_EDIT = "USER_EDIT";
    String USER_VIEW = "USER_VIEW";
    String USER_VIEW_LIST = "USER_VIEW_LIST";
    String USER_DELETE = "USER_DELETE";
  }

}
