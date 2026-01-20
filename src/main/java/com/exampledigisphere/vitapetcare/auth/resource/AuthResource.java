package com.exampledigisphere.vitapetcare.auth.resource;

import com.exampledigisphere.vitapetcare.admin.user.domain.User;
import com.exampledigisphere.vitapetcare.auth.DTO.LoginRequest;
import com.exampledigisphere.vitapetcare.auth.DTO.TokenRequest;
import com.exampledigisphere.vitapetcare.auth.useCases.GetCurrentAccountUseCase;
import com.exampledigisphere.vitapetcare.auth.useCases.LoginUseCase;
import com.exampledigisphere.vitapetcare.auth.useCases.RefreshTokenUseCase;
import com.exampledigisphere.vitapetcare.config.root.Info;
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

  public AuthResource(final LoginUseCase loginUseCase,
                      final RefreshTokenUseCase refreshTokenUseCase,
                      final GetCurrentAccountUseCase getCurrentAccountUseCase) {
    this.loginUseCase = loginUseCase;
    this.refreshTokenUseCase = refreshTokenUseCase;
    this.getCurrentAccountUseCase = getCurrentAccountUseCase;
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
}
