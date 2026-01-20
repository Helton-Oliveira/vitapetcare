package com.exampledigisphere.vitapetcare.auth.useCases;

import com.exampledigisphere.vitapetcare.auth.DTO.LoginRequest;
import com.exampledigisphere.vitapetcare.auth.DTO.LoginResponse;
import com.exampledigisphere.vitapetcare.config.root.Info;
import com.exampledigisphere.vitapetcare.config.security.JwtTokenUtil;
import com.exampledigisphere.vitapetcare.config.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Info(
  dev = Info.Dev.heltonOliveira,
  label = Info.Label.feature,
  date = "19/01/2026",
  description = "UseCase para realizar o login do usuário"
)
public class LoginUseCase {

  private final AuthenticationManager manager;
  private final JwtTokenUtil jwtTokenUtil;
  private final UserDetailsServiceImpl userDetailsServiceImpl;

  @Transactional(readOnly = true)
  @Info(
    dev = Info.Dev.heltonOliveira,
    label = Info.Label.feature,
    date = "19/01/2026",
    description = "Autentica as credenciais e gera os tokens de acesso e refresh"
  )
  public Optional<LoginResponse> execute(LoginRequest credentials) {
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
}
