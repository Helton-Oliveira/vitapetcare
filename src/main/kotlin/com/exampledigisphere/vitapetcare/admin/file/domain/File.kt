package com.exampledigisphere.vitapetcare.admin.file.domain

import com.exampledigisphere.vitapetcare.admin.user.domain.User
import com.exampledigisphere.vitapetcare.config.root.BaseEntity
import com.exampledigisphere.vitapetcare.config.security.Permissions
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonView
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import org.hibernate.Hibernate
import org.jetbrains.annotations.NotNull

@Entity
@Table(name = "fil_files")
class File : BaseEntity() {

  @field:Transient
  final val TABLE_NAME = "fil_files";

  @field:NotBlank
  @field:JsonView(Json.List::class)
  var name: String? = null;

  @field:NotBlank
  @field:JsonView(Json.List::class)
  var path: String? = null;

  @field:NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @field:JoinColumn(name = "user_id")
  @field:JsonView(Json.WithUser::class)
  @field:JsonIgnoreProperties("user")
  var user: User? = null;

  @field:NotNull
  @field:Enumerated(EnumType.STRING)
  @field:JsonView(Json.Detail::class)
  var type: FileType? = null;

  fun loadOwner() = Hibernate.initialize(user);

  interface Json {
    interface List;
    interface Detail : List;
    interface WithUser : User.Json.List
    interface All : Detail, WithUser;
  }

  object FilePermissions : Permissions {
    override var resource = "FILE"
  }
}
