package com.exampledigisphere.vitapetcare.auth.useCases;

import com.exampledigisphere.vitapetcare.auth.DTO.TokenRequest;
import com.exampledigisphere.vitapetcare.auth.DTO.TokenResponse;
import com.exampledigisphere.vitapetcare.config.root.Info;
import com.exampledigisphere.vitapetcare.config.security.JwtTokenUtil;
import com.exampledigisphere.vitapetcare.config.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
  description = "UseCase para renovar o token de acesso"
)
public class RefreshTokenUseCase {

  private final JwtTokenUtil jwtTokenUtil;
  private final UserDetailsServiceImpl userDetailsServiceImpl;

  @Transactional(readOnly = true)
  @Info(
    dev = Info.Dev.heltonOliveira,
    label = Info.Label.feature,
    date = "19/01/2026",
    description = "Valida o refresh token e gera um novo access token"
  )
  public Optional<TokenResponse> execute(TokenRequest request) {
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
}
