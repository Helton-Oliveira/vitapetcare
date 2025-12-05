package com.exampledigisphere.vitapetcare.user.useCases

import com.exampledigisphere.vitapetcare.user.domain.User
import com.exampledigisphere.vitapetcare.user.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
@Transactional
class CreateUser(private val userRepository: UserRepository, private val passwordEncoder: PasswordEncoder) {

  fun execute(user: User): Result<User> = runCatching {
    user.apply { password = passwordEncoder.encode(user.password) }
      .let { userRepository.save(it) }
  }
}
