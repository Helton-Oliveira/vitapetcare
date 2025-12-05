package com.exampledigisphere.vitapetcare.permission.domain

import com.exampledigisphere.vitapetcare.config.root.BaseEntity
import com.exampledigisphere.vitapetcare.roles.domain.Role
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ManyToMany
import jakarta.persistence.Table

@Entity
@Table(name = "per_permissions")
class Permission : BaseEntity() {
  lateinit var name: String;

  @ManyToMany(mappedBy = "permissions", fetch = FetchType.LAZY)
  val roles: MutableSet<Role> = HashSet()
}
