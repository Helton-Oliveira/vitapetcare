package com.exampledigisphere.vitapetcare.config.security

import com.exampledigisphere.vitapetcare.admin.user.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(
  private val userRepository: UserRepository,
) : UserDetailsService {

  override fun loadUserByUsername(email: String): UserDetails {
    val user = userRepository.findByUsername(email)
      ?: throw UsernameNotFoundException("User not found")

    return UserDetailsImpl(user)
  }
}
