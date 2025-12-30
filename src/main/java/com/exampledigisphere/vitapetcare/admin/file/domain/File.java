package com.exampledigisphere.vitapetcare.admin.file.domain;

import com.exampledigisphere.vitapetcare.admin.user.domain.User;
import com.exampledigisphere.vitapetcare.config.root.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

@Data
@Entity
@Table(name = "fil_files")
@NoArgsConstructor
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

  public interface Authority {
    String FILE_CREATE = "FILE_CREATE";
    String FILE_EDIT = "FILE_EDIT";
    String FILE_VIEW = "FILE_VIEW";
    String FILE_VIEW_LIST = "FILE_VIEW_LIST";
    String FILE_DELETE = "FILE_DELETE";
  }

}
