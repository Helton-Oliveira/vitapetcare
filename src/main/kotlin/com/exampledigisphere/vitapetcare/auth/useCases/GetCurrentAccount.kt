package com.exampledigisphere.vitapetcare.auth.useCases

import com.exampledigisphere.vitapetcare.config.root.extensions.getAuthenticatedUsername
import com.exampledigisphere.vitapetcare.user.domain.User
import com.exampledigisphere.vitapetcare.user.repository.UserRepository
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
