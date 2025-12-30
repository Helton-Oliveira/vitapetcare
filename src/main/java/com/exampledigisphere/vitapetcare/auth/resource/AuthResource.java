package com.exampledigisphere.vitapetcare.auth.resource;

import com.exampledigisphere.vitapetcare.auth.DTO.LoginRequest;
import com.exampledigisphere.vitapetcare.auth.DTO.TokenRequest;
import com.exampledigisphere.vitapetcare.auth.service.AuthService;
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

  private final AuthService authService;

  public AuthResource(AuthService authService) {
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
    return authService.getCurrentAccount()
      .map(ResponseEntity::ok)
      .orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
  }
}
