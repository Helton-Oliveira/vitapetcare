package com.exampledigisphere.vitapetcare.user.domain

import com.exampledigisphere.vitapetcare.config.root.BaseEntity
import com.exampledigisphere.vitapetcare.config.security.Permissions
import com.exampledigisphere.vitapetcare.roles.Role
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonView
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.hibernate.Hibernate

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
  @JsonIgnore
  lateinit var password: String

  @field:NotNull
  @field:Enumerated(EnumType.STRING)
  var role: Role = Role.NOT_INFORMED;

  @get:JsonView(Json.List::class)
  val permissions: Set<String>
    get() = role.permissions;

  fun loadRole() = Hibernate.initialize(role)

  interface Json {
    interface List;
    interface Detail : List;
    interface All : Detail;
  }

  object UserPermissions : Permissions {
    override var resource = "USER"
  }
}
