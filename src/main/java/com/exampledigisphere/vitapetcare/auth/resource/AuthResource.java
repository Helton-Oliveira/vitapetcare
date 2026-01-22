package com.exampledigisphere.vitapetcare.auth.resource;

import com.exampledigisphere.vitapetcare.admin.user.domain.User;
import com.exampledigisphere.vitapetcare.auth.DTO.ConfirmPasswordReset;
import com.exampledigisphere.vitapetcare.auth.DTO.LoginRequest;
import com.exampledigisphere.vitapetcare.auth.DTO.ResetPasswordRequest;
import com.exampledigisphere.vitapetcare.auth.DTO.TokenRequest;
import com.exampledigisphere.vitapetcare.auth.useCases.*;
import com.exampledigisphere.vitapetcare.config.root.Info;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Info(
  dev = Info.Dev.heltonOliveira,
  label = Info.Label.feature,
  date = "29/12/2025",
  description = "Recurso REST para autenticação"
)
public class AuthResource {

  private final LoginUseCase loginUseCase;
  private final RefreshTokenUseCase refreshTokenUseCase;
  private final GetCurrentAccountUseCase getCurrentAccountUseCase;
  private final RecoveryPasswordRequest recoveryPasswordRequest;
  private final ConfirmResetPassword confirmResetPassword;

  public AuthResource(final LoginUseCase loginUseCase,
                      final RefreshTokenUseCase refreshTokenUseCase,
                      final GetCurrentAccountUseCase getCurrentAccountUseCase,
                      final RecoveryPasswordRequest recoveryPasswordRequest,
                      final ConfirmResetPassword confirmResetPassword) {
    this.loginUseCase = loginUseCase;
    this.refreshTokenUseCase = refreshTokenUseCase;
    this.getCurrentAccountUseCase = getCurrentAccountUseCase;
    this.recoveryPasswordRequest = recoveryPasswordRequest;
    this.confirmResetPassword = confirmResetPassword;
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequest credentials) {
    return loginUseCase.execute(credentials)
      .map(ResponseEntity::ok)
      .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
  }

  @PostMapping("/refresh")
  public ResponseEntity<?> refresh(@RequestBody TokenRequest request) {
    return refreshTokenUseCase.execute(request)
      .map(ResponseEntity::ok)
      .orElseGet(() -> ResponseEntity.status(HttpStatus.CONFLICT).build());
  }

  @GetMapping("/current-account")
  public ResponseEntity<?> getCurrentUser() {
    return getCurrentAccountUseCase.execute()
      .map(User::loadFiles)
      .map(ResponseEntity::ok)
      .orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
  }

  @PostMapping("/forgot-password")
  public ResponseEntity<?> recoverPassword(@RequestBody @Valid ResetPasswordRequest resetPasswordRequest) {
    final var res = recoveryPasswordRequest.execute(resetPasswordRequest);
    return ResponseEntity.ok(res);
  }

  @PostMapping("/new-password")
  public ResponseEntity<?> confirmNewPassword(@RequestBody @Valid ConfirmPasswordReset confirmPasswordReset) {
    final var res = confirmResetPassword.execute(confirmPasswordReset);
    return ResponseEntity.ok(res);
  }
}
