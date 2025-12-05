package com.exampledigisphere.vitapetcare.roles.domain

import com.exampledigisphere.vitapetcare.config.root.BaseEntity
import com.exampledigisphere.vitapetcare.permission.domain.Permission
import com.exampledigisphere.vitapetcare.user.domain.User
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonView
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.hibernate.Hibernate

@Table(name = "rol_roles")
@Entity
class Role : BaseEntity() {

  @field:NotBlank
  @field:JsonView(Json.List::class)
  lateinit var name: String

  @field:NotNull
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
    name = "roles_permissions",
    joinColumns = [JoinColumn(name = "role_id")],
    inverseJoinColumns = [JoinColumn(name = "permission_id")]
  )
  @field:JsonView(Json.Detail::class)
  var permissions: MutableSet<Permission> = HashSet()

  @field:NotBlank
  @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
  @field:JsonIgnoreProperties("role")
  @field:JsonView(Json.Detail::class)
  var users: MutableSet<User> = HashSet()

  fun loadUsers() = Hibernate.initialize(users)

  interface Json {
    interface List;
    interface Detail : List;
    interface All : Detail;
  }

  interface Permissions {
    companion object {
      const val ROLE_CREATE = "ROLE_CREATE"
      const val ROLE_VIEW = "ROLE_VIEW"
      const val ROLE_EDIT = "ROLE_EDIT"
      const val ROLE_DELETE = "ROLE_DELETE"

      fun all() = setOf(ROLE_CREATE, ROLE_VIEW, ROLE_EDIT, ROLE_DELETE)
    }
  }
}
