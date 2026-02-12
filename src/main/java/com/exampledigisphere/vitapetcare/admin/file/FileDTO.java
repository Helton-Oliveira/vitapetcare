package com.exampledigisphere.vitapetcare.admin.file;

import com.exampledigisphere.vitapetcare.admin.file.domain.FileType;
import com.exampledigisphere.vitapetcare.admin.user.UserDTO;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public class FileDTO implements Serializable {
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
  private String path;

  @NotNull
  @JsonView(Json.Detail.class)
  private FileType type;

  @JsonView(Json.WithUser.class)
  private UserDTO user;

  @NotNull
  @JsonView(Json.List.class)
  private Boolean edited;

  @NotNull
  @JsonView(Json.List.class)
  private Boolean active;

  public FileDTO() {
  }

  public FileDTO(Long id, String uuid, String name, String path, FileType type, UserDTO user, Boolean edited, Boolean active) {
    this.id = id;
    this.uuid = uuid;
    this.name = name;
    this.path = path;
    this.type = type;
    this.user = user;
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

  public String getPath() {
    return path;
  }

  public FileType getType() {
    return type;
  }

  public UserDTO getUser() {
    return user;
  }

  public Boolean getEdited() {
    return edited;
  }

  public Boolean getActive() {
    return active;
  }

  public void setUser(UserDTO user) {
    this.user = user;
  }

  public interface Json {
    interface List {
    }

    interface Detail extends List {
    }

    interface WithUser extends UserDTO.Json.Detail {
    }

    interface All extends Detail, WithUser {
    }
  }
}
