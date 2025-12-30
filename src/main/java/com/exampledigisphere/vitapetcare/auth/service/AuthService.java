package com.exampledigisphere.vitapetcare.auth.service;

import com.exampledigisphere.vitapetcare.admin.user.domain.UserMapper;
import com.exampledigisphere.vitapetcare.admin.user.domain.UserOutput;
import com.exampledigisphere.vitapetcare.admin.user.repository.UserRepository;
import com.exampledigisphere.vitapetcare.auth.DTO.LoginRequest;
import com.exampledigisphere.vitapetcare.auth.DTO.LoginResponse;
import com.exampledigisphere.vitapetcare.auth.DTO.TokenRequest;
import com.exampledigisphere.vitapetcare.auth.DTO.TokenResponse;
import com.exampledigisphere.vitapetcare.config.root.Info;
import com.exampledigisphere.vitapetcare.config.root.util.SecurityUtils;
import com.exampledigisphere.vitapetcare.config.security.JwtTokenUtil;
import com.exampledigisphere.vitapetcare.config.security.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@Info(
  dev = Info.Dev.heltonOliveira,
  label = Info.Label.feature,
  date = "29/12/2025",
  description = "Serviço de autenticação consolidando login, refresh token e obtenção de conta atual"
)
public class AuthService {

  private final AuthenticationManager manager;
  private final JwtTokenUtil jwtTokenUtil;
  private final UserDetailsServiceImpl userDetailsServiceImpl;
  private final UserRepository userRepository;
  private final UserMapper userMapper;

  public AuthService(final AuthenticationManager manager,
                     final JwtTokenUtil jwtTokenUtil,
                     final UserDetailsServiceImpl userDetailsServiceImpl,
                     final UserRepository userRepository,
                     final UserMapper userMapper) {
    this.manager = manager;
    this.jwtTokenUtil = jwtTokenUtil;
    this.userDetailsServiceImpl = userDetailsServiceImpl;
    this.userRepository = userRepository;
    this.userMapper = userMapper;
  }

  public Optional<LoginResponse> login(LoginRequest credentials) {
    log.info("Iniciando login para o usuário: {}", credentials.email());
    try {
      Authentication auth = manager.authenticate(
        new UsernamePasswordAuthenticationToken(credentials.email(), credentials.password())
      );
      SecurityContextHolder.getContext().setAuthentication(auth);

      String username = auth.getName();
      UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(username);

      String accessToken = jwtTokenUtil.generateAccessToken(
        userDetails.getUsername(),
        userDetails.getAuthorities()
      );

      String refreshToken = jwtTokenUtil.generateRefreshToken(
        userDetails.getUsername()
      );

      return Optional.of(new LoginResponse(refreshToken, accessToken));
    } catch (Exception e) {
      log.error("Erro ao realizar login para o usuário: {}. Erro: {}", credentials.email(), e.getMessage());
      return Optional.empty();
    }
  }

  public Optional<TokenResponse> refresh(TokenRequest request) {
    log.info("Iniciando renovação de token");
    try {
      if (!jwtTokenUtil.validate(request.refreshToken())) {
        throw new IllegalArgumentException("Invalid refresh token");
      }

      String username = jwtTokenUtil.extractUsername(request.refreshToken());
      UserDetails user = userDetailsServiceImpl.loadUserByUsername(username);

      String newAccessToken = jwtTokenUtil.generateAccessToken(
        username,
        user.getAuthorities()
      );

      return Optional.of(new TokenResponse(newAccessToken));
    } catch (Exception e) {
      log.error("Erro ao renovar token: {}", e.getMessage());
      return Optional.empty();
    }
  }

  public Optional<UserOutput> getCurrentAccount() {
    String username = SecurityUtils.getAuthenticatedUsername();
    log.info("Buscando conta atual para o usuário: {}", username);
    return userRepository.findByUsername(username)
      .map(userMapper::toOutput);
  }
}
