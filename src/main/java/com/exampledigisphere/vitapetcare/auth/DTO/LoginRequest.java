package com.exampledigisphere.vitapetcare.auth.DTO;

import com.exampledigisphere.vitapetcare.config.root.Info;
import jakarta.validation.constraints.NotBlank;

@Info(
  dev = Info.Dev.heltonOliveira,
  label = Info.Label.doc,
  date = "29/12/2025",
  description = "Requisição de login"
)
public record LoginRequest(
  @NotBlank String email,
  @NotBlank String password
) {
}
