package com.exampledigisphere.vitapetcare.admin.user;

import com.exampledigisphere.vitapetcare.admin.file.FileDTO;
import com.exampledigisphere.vitapetcare.admin.workDay.WorkDayDTO;
import com.exampledigisphere.vitapetcare.auth.roles.Role;
import com.exampledigisphere.vitapetcare.auth.roles.RoleAuthorityMapper;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class UserDTO implements Serializable {
  private static final long serialVersionUID = 1L;

  @JsonView(Json.List.class)
  private Long id;

  @JsonView(Json.List.class)
  private String uuid;

  @NotBlank
  @JsonView(Json.List.class)
  private String name;

  @NotBlank
  @JsonView(Json.List.class)
  private String email;

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String password;

  @NotNull
  @JsonView(Json.List.class)
  private Role role;

  @JsonView(Json.List.class)
  private Set<String> authorities = new HashSet<>();

  @JsonView(Json.WithFIle.class)
  private Set<FileDTO> files;

  @JsonView(Json.WithWorkDay.class)
  private Set<WorkDayDTO> workDays;

  @NotNull
  @JsonView(Json.List.class)
  private Boolean edited;

  @NotNull
  @JsonView(Json.List.class)
  private Boolean active;

  public UserDTO() {
  }

  public UserDTO(Long id, String uuid, String name, String email, String password, Role role, Set<FileDTO> files, Set<WorkDayDTO> workDays, Boolean edited, Boolean active) {
    this.id = id;
    this.uuid = uuid;
    this.name = name;
    this.email = email;
    this.password = password;
    this.role = role;
    this.authorities = RoleAuthorityMapper.getAuthorities(role);
    this.files = files;
    this.workDays = workDays;
    this.edited = edited;
    this.active = active;
  }

  public Long getId() {
    return id;
  }

  public String getUuid() {
    return uuid;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }

  public Role getRole() {
    return role;
  }

  public Set<FileDTO> getFiles() {
    return files != null ? files : Collections.emptySet();
  }

  public Set<WorkDayDTO> getWorkDays() {
    return workDays != null ? workDays : Collections.emptySet();
  }

  public Boolean getEdited() {
    return edited;
  }

  public Boolean getActive() {
    return active;
  }

  public void setFiles(Set<FileDTO> files) {
    this.files = files;
  }

  public void setWorkDays(Set<WorkDayDTO> workDays) {
    this.workDays = workDays;
  }

  public Optional<Set<FileDTO>> editedFiles() {
    return Optional.ofNullable(files)
      .map(f -> f.stream()
        .filter(Objects::nonNull)
        .filter(FileDTO::getEdited)
        .collect(Collectors.toSet()));
  }

  public Optional<Set<WorkDayDTO>> editedWorkDays() {
    return Optional.ofNullable(workDays)
      .map(w -> w.stream()
        .filter(Objects::nonNull)
        .filter(WorkDayDTO::getEdited)
        .collect(Collectors.toSet()));
  }

  public interface Json {
    interface List {
    }

    interface Detail extends List {
    }

    interface WithFIle extends FileDTO.Json.List {
    }

    interface WithWorkDay extends WorkDayDTO.Json.List {
    }

    interface All extends Detail, WithFIle, WithWorkDay {
    }
  }
}
