package com.exampledigisphere.vitapetcare.config.security

import com.exampledigisphere.vitapetcare.roles.PermissionFactory
import com.exampledigisphere.vitapetcare.user.domain.User
import com.exampledigisphere.vitapetcare.user.repository.UserRepository
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(
  private val userRepository: UserRepository,
  private val permissionFactory: PermissionFactory
) : UserDetailsService {

  override fun loadUserByUsername(email: String): UserDetails {
    return userRepository.findByUsername(email)
      ?.let { user ->
        val authorities = getAuthorities(user)
        UserDetailsImpl(user, authorities)
      }
      ?: throw UsernameNotFoundException("User not found")
  }

  private fun getAuthorities(user: User): Collection<GrantedAuthority> {
    val authorities = HashSet<SimpleGrantedAuthority>()

    if (user.role?.name?.equals("SYS_ADMIN", ignoreCase = true) == true) {
      permissionFactory.getDefinedAuthorities().forEach { authString ->
        authorities.add(SimpleGrantedAuthority(authString))
      }
      return authorities
    }

    user.role?.permissions?.forEach { permission ->
      authorities.add(SimpleGrantedAuthority(permission.name))
    }

    return authorities
  }
}
