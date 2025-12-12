package com.exampledigisphere.vitapetcare.user.useCases

import com.exampledigisphere.vitapetcare.user.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class Disable(
  private val userRepository: UserRepository
) {

  fun execute(id: Long): Result<*> =
    runCatching {
      val user = userRepository.findById(id)
        .takeIf { it.isPresent }
        ?.get()

      user?.disabled();

      user.takeIf { it != null }
        ?.let(userRepository::save)
    }
}
