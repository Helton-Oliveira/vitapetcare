package com.exampledigisphere.vitapetcare.admin.user.useCases

import com.exampledigisphere.vitapetcare.admin.user.domain.User
import com.exampledigisphere.vitapetcare.admin.user.repository.UserRepository
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
