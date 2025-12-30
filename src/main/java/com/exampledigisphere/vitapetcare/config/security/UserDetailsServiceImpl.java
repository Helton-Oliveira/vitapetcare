package com.exampledigisphere.vitapetcare.config.security;

import com.exampledigisphere.vitapetcare.admin.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;

  public UserDetailsServiceImpl(final UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String email) {
    final var user = userRepository.findByUsername(email)
      .orElseThrow();

    return new UserDetailsImpl(user);
  }
}
