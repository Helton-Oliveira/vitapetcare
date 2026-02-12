package com.exampledigisphere.vitapetcare.auth;

import com.exampledigisphere.vitapetcare.admin.user.UserDTO;
import com.exampledigisphere.vitapetcare.admin.user.UserFactory;
import com.exampledigisphere.vitapetcare.admin.user.domain.User;
import com.exampledigisphere.vitapetcare.admin.user.domain.UserAssociations;
import com.exampledigisphere.vitapetcare.admin.user.repository.UserRepository;
import com.exampledigisphere.vitapetcare.auth.DTO.*;
import com.exampledigisphere.vitapetcare.config.root.Info;
import com.exampledigisphere.vitapetcare.config.root.util.SecurityUtils;
import com.exampledigisphere.vitapetcare.config.security.JwtTokenUtil;
import com.exampledigisphere.vitapetcare.config.security.UserDetailsServiceImpl;
import com.exampledigisphere.vitapetcare.mail.SendMail;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Optional;
import java.util.Set;

import static com.exampledigisphere.vitapetcare.config.root.Utils.initialize;
import static com.exampledigisphere.vitapetcare.config.root.Utils.transformToAssociationSet;

@Slf4j
@Service
@Transactional
@Info(
  dev = Info.Dev.heltonOliveira,
  label = Info.Label.feature,
  date = "06/02/2026",
  description = "Serviço centralizado para gestão de autenticação e conta"
)
public class AuthService {

  private final AuthenticationManager authenticationManager;
  private final JwtTokenUtil jwtTokenUtil;
  private final UserDetailsServiceImpl userDetailsServiceImpl;
  private final UserRepository userRepository;
  private final SendMail sendMail;
  private final TemplateEngine templateEngine;

  public AuthService(final AuthenticationManager authenticationManager, final JwtTokenUtil jwtTokenUtil,
                     final UserDetailsServiceImpl userDetailsServiceImpl, final UserRepository userRepository,
                     final SendMail sendMail, final TemplateEngine templateEngine) {
    this.authenticationManager = authenticationManager;
    this.jwtTokenUtil = jwtTokenUtil;
    this.userDetailsServiceImpl = userDetailsServiceImpl;
    this.userRepository = userRepository;
    this.sendMail = sendMail;
    this.templateEngine = templateEngine;
  }

  @Transactional(readOnly = true)
  @Info(
    dev = Info.Dev.heltonOliveira,
    label = Info.Label.feature,
    date = "06/02/2026",
    description = "Autentica as credenciais e gera os tokens de acesso e refresh"
  )
  public Optional<LoginResponse> login(@NonNull LoginRequest credentials) {
    log.info("Iniciando login para o usuário: {}", credentials.email());
    try {
      Authentication auth = authenticationManager.authenticate(
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

  @Transactional(readOnly = true)
  @Info(
    dev = Info.Dev.heltonOliveira,
    label = Info.Label.feature,
    date = "06/02/2026",
    description = "Valida o refresh token e gera um novo access token"
  )
  public Optional<TokenResponse> refresh(@NonNull TokenRequest request) {
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

  @Transactional(readOnly = true)
  @Info(
    dev = Info.Dev.heltonOliveira,
    label = Info.Label.feature,
    date = "06/02/2026",
    description = "Busca o usuário autenticado no banco de dados com associações"
  )
  public Optional<UserDTO> getCurrentAccount(Set<UserAssociations> associations) {
    String username = SecurityUtils.getAuthenticatedUsername();
    log.info("Buscando conta atual para o usuário: {}", username);

    return userRepository.findByUsername(username)
      .map(usr -> usr.applyAssociations(transformToAssociationSet(associations)))
      .map(user -> initialize(user, (associations)))
      .map(UserFactory::toResponse);
  }

  @Info(
    dev = Info.Dev.heltonOliveira,
    label = Info.Label.feature,
    date = "06/02/2026",
    description = "Inicia o processo de recuperação de senha enviando um e-mail com link de reset"
  )
  public boolean recoveryPasswordRequest(@NonNull ResetPasswordRequest input) {
    log.info("Iniciando recuperação de senha para: {}", input.email());
    return userRepository.findByUsername(input.email())
      .map(user -> {
        try {
          processPasswordRecovery(user, input.url());
          return true;
        } catch (Exception e) {
          log.error("Erro ao processar recuperação de senha para {}: {}", user.getEmail(), e.getMessage());
          return false;
        }
      })
      .orElse(false);
  }

  @Info(
    dev = Info.Dev.heltonOliveira,
    label = Info.Label.feature,
    date = "06/02/2026",
    description = "Confirma o reset da senha utilizando o código de verificação"
  )
  public boolean confirmResetPassword(@NonNull ConfirmPasswordReset input) {
    log.info("Confirmando reset de senha para o código: {}", input.resetCode());

    return userRepository.findByResetCode(input.resetCode())
      .map(
        (user) -> {
          if (user.verifyResetCode(input.resetCode())) {
            user.setPassword(input.newPassword());
            user.encryptPassword();
            user.markResetCodeUsed();
            userRepository.save(user);
            return true;
          }
          return false;
        })
      .orElse(false);
  }

  private void processPasswordRecovery(User user, String baseUrl) {
    user.generateResetCode();

    final var resetLink = String.format("%s?resetCode=%s", baseUrl, user.getResetCode());
    final var context = new Context();
    context.setVariable("user", user);
    context.setVariable("resetLink", resetLink);

    sendMail.execute(
      "contato@vitapetcare.com",
      user.getEmail(),
      "Recuperação de Senha",
      templateEngine.process("password-reset", context)
    );

    userRepository.save(user);
  }
}
