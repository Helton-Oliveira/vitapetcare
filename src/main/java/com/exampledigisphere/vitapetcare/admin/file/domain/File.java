package com.exampledigisphere.vitapetcare.admin.file.domain;

import com.exampledigisphere.vitapetcare.admin.user.domain.User;
import com.exampledigisphere.vitapetcare.config.root.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.Hibernate;

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
  @JsonView(Json.WithUser.class)
  @JsonIgnoreProperties("user")
  private User user;

  @NotNull
  @Enumerated(EnumType.STRING)
  @JsonView(Json.Detail.class)
  private FileType type;

  public File loadOwner() {
    Hibernate.initialize(user);
    return this;
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

  @NotNull
  public User getUser() {
    return user;
  }

  public void setUser(@NotNull User user) {
    this.user = user;
  }

  @NotNull
  public FileType getType() {
    return type;
  }

  public void setType(@NotNull FileType type) {
    this.type = type;
  }

  public interface Json {
    interface List {
    }

    interface Detail extends List {
    }

    interface WithUser extends User.Json.List {
    }

    interface All extends Detail, WithUser {
    }
  }

}
