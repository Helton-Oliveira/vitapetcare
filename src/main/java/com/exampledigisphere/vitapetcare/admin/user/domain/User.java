package com.exampledigisphere.vitapetcare.admin.user.domain;

import com.exampledigisphere.vitapetcare.admin.file.domain.File;
import com.exampledigisphere.vitapetcare.admin.workDay.domain.WorkDay;
import com.exampledigisphere.vitapetcare.auth.roles.Role;
import com.exampledigisphere.vitapetcare.auth.roles.RoleAuthorityMapper;
import com.exampledigisphere.vitapetcare.config.root.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.Hibernate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "usr_users")
public class User extends BaseEntity implements Serializable {

  @NotBlank
  @JsonView(Json.List.class)
  private String name;

  @NotBlank
  @JsonView(Json.List.class)
  private String email;

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String password;

  @Enumerated(EnumType.STRING)
  @JsonView(Json.List.class)
  private Role role;

  @Transient
  @JsonView(Json.List.class)
  private Set<String> authorities = new HashSet<>();

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
  @JsonView(Json.WithFIle.class)
  @JsonIgnoreProperties("user")
  private Set<File> files = new HashSet<>();

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
  @JsonView(Json.WithWorkDay.class)
  @JsonIgnoreProperties("user")
  private Set<WorkDay> workDays = new HashSet<>();

  public User() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public User setPassword(String password) {
    this.password = password;
    return this;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public Set<File> getFiles() {
    return files;
  }

  public void setFiles(Set<File> files) {
    this.files = files;
  }

  public Set<WorkDay> getWorkDays() {
    return workDays;
  }

  public void setWorkDays(Set<WorkDay> workDays) {
    this.workDays = workDays;
  }

  @Transient
  @JsonView(Json.List.class)
  @JsonProperty("authorities")
  public Set<String> getAuthorities() {
    if (this.role == null) {
      return Collections.emptySet();
    }
    return RoleAuthorityMapper.getAuthorities(this.role);
  }

  public void setAuthorities(Set<String> authorities) {
    this.authorities = authorities;
  }

  public User loadFiles() {
    Hibernate.initialize(files);
    return this;
  }

  public User loadWorkDays() {
    Hibernate.initialize(workDays);
    return this;
  }

  public void encryptPassword() {
    if (!this.password.isBlank()) this.password = new BCryptPasswordEncoder().encode(this.password);
  }

  public void prepareForCreation() {
    this.files = null;
    this.workDays = null;
  }

  public interface Authority {
    String USER_CREATE = "USER_CREATE";
    String USER_EDIT = "USER_EDIT";
    String USER_VIEW = "USER_VIEW";
    String USER_VIEW_LIST = "USER_VIEW_LIST";
    String USER_DELETE = "USER_DELETE";
  }

  interface Json {
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
}
