package com.exampledigisphere.vitapetcare.config.security

import com.exampledigisphere.vitapetcare.admin.user.domain.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserDetailsImpl(private val user: User?) : UserDetails {

  override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
    val authorities = mutableListOf<GrantedAuthority>()
    authorities.add(SimpleGrantedAuthority("ROLE_${user?.role?.name}"))
    user?.role?.permissions?.forEach { perm ->
      authorities.add(SimpleGrantedAuthority(perm))
    }
    return authorities
  }

  override fun getPassword(): String = user?.password!!
  override fun getUsername(): String = user?.email!!

  override fun isAccountNonExpired(): Boolean = true
  override fun isAccountNonLocked(): Boolean = true
  override fun isCredentialsNonExpired(): Boolean = true
  override fun isEnabled(): Boolean = true
}
