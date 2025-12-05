package com.exampledigisphere.vitapetcare.roles.useCase

import com.exampledigisphere.vitapetcare.roles.domain.Role
import com.exampledigisphere.vitapetcare.roles.repository.RoleRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
@Transactional
class CreateRole(
  private val roleRepository: RoleRepository
) {

  fun execute(role: Role): Result<Role> = runCatching {
    roleRepository.save(role)
  }
}
