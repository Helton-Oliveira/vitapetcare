package com.exampledigisphere.vitapetcare.admin.file.domain;

import com.exampledigisphere.vitapetcare.admin.user.domain.User;
import com.exampledigisphere.vitapetcare.config.root.BaseEntity;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.Hibernate;

import java.util.Objects;

@Entity
@Table(name = "fil_files")
public class File extends BaseEntity {

  @Transient
  final String TABLE_NAME = "fil_files";

  @NotBlank
  @JsonView(Json.List.class)
  private String name;

  @NotBlank
  @JsonView(Json.List.class)
  private String path;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @NotNull
  @Enumerated(EnumType.STRING)
  @JsonView(Json.Detail.class)
  private FileType type;

  public File() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public FileType getType() {
    return type;
  }

  public void setType(FileType type) {
    this.type = type;
  }

  public File loadUser() {
    Hibernate.initialize(user);
    return this;
  }

  public interface Authority {
    String FILE_CREATE = "FILE_CREATE";
    String FILE_EDIT = "FILE_EDIT";
    String FILE_VIEW = "FILE_VIEW";
    String FILE_VIEW_LIST = "FILE_VIEW_LIST";
    String FILE_DELETE = "FILE_DELETE";
  }

  @Override
  public boolean equals(Object object) {
    if (object == null || getClass() != object.getClass()) return false;
    if (!super.equals(object)) return false;
    File file = (File) object;
    return Objects.equals(name, file.name)
      && Objects.equals(path, file.path)
      && Objects.equals(user, file.user)
      && type == file.type;
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), TABLE_NAME, name, path, user, type);
  }
}
