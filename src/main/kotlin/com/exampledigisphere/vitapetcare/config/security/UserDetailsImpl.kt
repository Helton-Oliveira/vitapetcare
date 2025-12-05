package com.exampledigisphere.vitapetcare.config.security

import com.exampledigisphere.vitapetcare.user.domain.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserDetailsImpl(private val user: User?, private val authorities: Collection<GrantedAuthority>) : UserDetails {

  override fun getAuthorities(): Collection<GrantedAuthority?> = authorities

  override fun getPassword(): String? = user?.password

  override fun getUsername(): String? = user?.email
}
