package com.exampledigisphere.vitapetcare.user.domain

import com.exampledigisphere.vitapetcare.config.root.BaseEntity
import com.exampledigisphere.vitapetcare.roles.domain.Role
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonView
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
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
  lateinit var password: String

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "role_id")
  @field:JsonIgnoreProperties("role")
  @field:JsonView(Role.Json.Detail::class)
  val role: Role? = null;

  fun loadRole() = Hibernate.initialize(role)

  interface Json {
    interface List;
    interface Detail : List;
    interface All : Detail;
  }

  interface Permissions {
    companion object {
      const val USER_CREATE = "USER_CREATE"
      const val USER_VIEW = "USER_VIEW"
      const val USER_EDIT = "USER_EDIT"
      const val USER_DELETE = "USER_DELETE"

      fun all() = setOf(USER_CREATE, USER_VIEW, USER_EDIT, USER_DELETE)
    }
  }
}
