package com.exampledigisphere.vitapetcare.auth.useCases

import com.exampledigisphere.vitapetcare.admin.user.domain.User
import com.exampledigisphere.vitapetcare.admin.user.repository.UserRepository
import com.exampledigisphere.vitapetcare.config.root.extensions.getAuthenticatedUsername
import org.springframework.stereotype.Service

@Service
class GetCurrentAccount(
  private val userRepository: UserRepository,
) {

  fun execute(): Result<User?> =
    runCatching {
      userRepository.findByUsername(getAuthenticatedUsername())
    }

}
