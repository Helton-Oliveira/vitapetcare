package com.exampledigisphere.vitapetcare.roles.useCase

import com.exampledigisphere.vitapetcare.permission.domain.Permission
import com.exampledigisphere.vitapetcare.permission.repository.PermissionRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
@Transactional
class GetAllPermissions(
  private val permissionRepository: PermissionRepository
) {

  fun execute(): Result<List<Permission>> = runCatching {
    permissionRepository.findAll()
  }
}
