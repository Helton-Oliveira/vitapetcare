package com.exampledigisphere.vitapetcare.admin.user.domain;

import com.exampledigisphere.vitapetcare.admin.file.domain.File;
import com.exampledigisphere.vitapetcare.admin.role.Role;
import com.exampledigisphere.vitapetcare.admin.workDay.domain.WorkDay;
import com.exampledigisphere.vitapetcare.config.root.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.Hibernate;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "usr_users")
public class User extends BaseEntity {

  @NotBlank
  @JsonView(Json.List.class)
  private String name;

  @NotBlank
  @JsonView(Json.List.class)
  private String email;

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String password;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
    name = "user_roles",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles = new HashSet<>();

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
  @JsonIgnoreProperties("user")
  @JsonView(Json.WithFIle.class)
  private Set<File> files = new HashSet();

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
  @JsonIgnoreProperties("user")
  @JsonView(Json.WithFIle.class)
  private Set<WorkDay> workDays = new HashSet();

  public User loadRoles() {
    Hibernate.initialize(roles);
    return this;
  }

  public User loadFiles() {
    Hibernate.initialize(files);
    return this;
  }

  public User loadWorkDay() {
    Hibernate.initialize(workDays);
    return this;
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

  public void setPassword(String password) {
    this.password = password;
  }

  public Set<Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
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

}
