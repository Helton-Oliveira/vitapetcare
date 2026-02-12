package com.exampledigisphere.vitapetcare.auth.resource;

import com.exampledigisphere.vitapetcare.admin.user.domain.UserAssociations;
import com.exampledigisphere.vitapetcare.auth.AuthService;
import com.exampledigisphere.vitapetcare.auth.DTO.ConfirmPasswordReset;
import com.exampledigisphere.vitapetcare.auth.DTO.LoginRequest;
import com.exampledigisphere.vitapetcare.auth.DTO.ResetPasswordRequest;
import com.exampledigisphere.vitapetcare.auth.DTO.TokenRequest;
import com.exampledigisphere.vitapetcare.config.root.Info;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/auth")
@Info(
  dev = Info.Dev.heltonOliveira,
  label = Info.Label.feature,
  date = "06/02/2026",
  description = "Recurso REST para autenticação"
)
public class AuthResource {

  private final AuthService authService;

  public AuthResource(final AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequest credentials) {
    return authService.login(credentials)
      .map(ResponseEntity::ok)
      .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
  }

  @PostMapping("/refresh")
  public ResponseEntity<?> refresh(@RequestBody TokenRequest request) {
    return authService.refresh(request)
      .map(ResponseEntity::ok)
      .orElseGet(() -> ResponseEntity.status(HttpStatus.CONFLICT).build());
  }

  @GetMapping("/current-account")
  public ResponseEntity<?> getCurrentUser() {
    return authService.getCurrentAccount(Set.of(UserAssociations.FILES))
      .map(ResponseEntity::ok)
      .orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
  }

  @PostMapping("/forgot-password")
  public ResponseEntity<?> recoverPassword(@RequestBody @Valid ResetPasswordRequest resetPasswordRequest) {
    final var res = authService.recoveryPasswordRequest(resetPasswordRequest);
    return ResponseEntity.ok(res);
  }

  @PostMapping("/new-password")
  public ResponseEntity<?> confirmNewPassword(@RequestBody @Valid ConfirmPasswordReset confirmPasswordReset) {
    final var res = authService.confirmResetPassword(confirmPasswordReset);
    return ResponseEntity.ok(res);
  }
}
