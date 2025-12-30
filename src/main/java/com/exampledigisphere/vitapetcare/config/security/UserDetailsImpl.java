package com.exampledigisphere.vitapetcare.config.security;

import com.exampledigisphere.vitapetcare.admin.permission.Permission;
import com.exampledigisphere.vitapetcare.admin.role.Role;
import com.exampledigisphere.vitapetcare.admin.user.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class UserDetailsImpl implements UserDetails {
  private final User user;

  public UserDetailsImpl(final User user) {
    this.user = user;
  }

  public Collection<? extends GrantedAuthority> getAuthorities() {
    Set<GrantedAuthority> authorities = new HashSet<>();
    for (Role role : user.getRoles()) {
      authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
      for (Permission permission : role.getPermissions()) {
        authorities.add(new SimpleGrantedAuthority(permission.getName()));
      }
    }
    return authorities;
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getEmail();
  }

  @Override
  public boolean isAccountNonExpired() {
    return UserDetails.super.isAccountNonExpired();
  }

  @Override
  public boolean isAccountNonLocked() {
    return UserDetails.super.isAccountNonLocked();
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return UserDetails.super.isCredentialsNonExpired();
  }

  @Override
  public boolean isEnabled() {
    return UserDetails.super.isEnabled();
  }
}
