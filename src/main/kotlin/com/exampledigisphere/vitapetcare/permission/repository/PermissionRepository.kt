package com.exampledigisphere.vitapetcare.permission.repository

import com.exampledigisphere.vitapetcare.permission.domain.Permission
import org.springframework.data.jpa.repository.JpaRepository

interface PermissionRepository : JpaRepository<Permission, Long> {
}
