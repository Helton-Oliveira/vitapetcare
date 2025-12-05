package com.exampledigisphere.vitapetcare.roles.repository

import com.exampledigisphere.vitapetcare.roles.domain.Role
import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository : JpaRepository<Role, Long> {
}
