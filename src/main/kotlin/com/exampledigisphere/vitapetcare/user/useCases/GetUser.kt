package com.exampledigisphere.vitapetcare.user.useCases

import com.exampledigisphere.vitapetcare.user.domain.User
import com.exampledigisphere.vitapetcare.user.repository.UserRepository
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class GetUser(
  private val userRepository: UserRepository
) {

  fun execute(id: Long): Result<User?> = runCatching {
    userRepository.findById(id).getOrNull()
  }
}
