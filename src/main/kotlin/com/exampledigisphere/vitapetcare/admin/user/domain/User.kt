package com.exampledigisphere.vitapetcare.admin.user.domain

import com.exampledigisphere.vitapetcare.admin.file.domain.File
import com.exampledigisphere.vitapetcare.admin.workDay.domain.WorkDay
import com.exampledigisphere.vitapetcare.auth.roles.Role
import com.exampledigisphere.vitapetcare.config.root.BaseEntity
import com.exampledigisphere.vitapetcare.config.security.Permissions
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonView
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.hibernate.Hibernate
import java.util.Collections.emptySet

@Entity
@Table(name = "usr_users")
class User : BaseEntity() {

  @field:NotBlank
  @field:JsonView(Json.List::class)
  lateinit var name: String

  @field:NotBlank
  @field:JsonView(Json.List::class)
  lateinit var email: String

  @field:NotBlank
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  lateinit var password: String

  @field:NotNull
  @field:Enumerated(EnumType.STRING)
  var role: Role = Role.NOT_INFORMED;

  @get:JsonView(Json.List::class)
  val permissions: Set<String>
    get() = role.permissions;

  @field:OneToMany(mappedBy = "user", cascade = [(CascadeType.ALL)], fetch = FetchType.LAZY)
  @field:JsonIgnoreProperties("user")
  @field:JsonView(Json.WithFIle::class)
  var files: MutableSet<File?> = emptySet();

  @field:OneToMany(mappedBy = "user", cascade = [(CascadeType.ALL)], fetch = FetchType.LAZY)
  @field:JsonIgnoreProperties("user")
  @field:JsonView(Json.WithFIle::class)
  var workDays: MutableSet<WorkDay?> = emptySet();

  fun loadRole() = Hibernate.initialize(role)

  fun loadFiles() = Hibernate.initialize(files)

  fun loadWorkDay() = Hibernate.initialize(workDays)

  interface Json {
    interface List;
    interface Detail : List;
    interface WithFIle : File.Json.List
    interface WithWorkDay : WorkDay.Json.List
    interface All : Detail, WithFIle, WithWorkDay;
  }

  object UserPermissions : Permissions {
    override var resource = "USER"
  }
}
