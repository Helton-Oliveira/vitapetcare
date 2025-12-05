package com.exampledigisphere.vitapetcare.user.useCases

import com.exampledigisphere.vitapetcare.user.domain.User
import com.exampledigisphere.vitapetcare.user.repository.UserRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class GetAll(
  private val userRepository: UserRepository
) {

  fun execute(page: Pageable): Result<Page<User>> = runCatching {
    userRepository.findAll(page)
  }
}
