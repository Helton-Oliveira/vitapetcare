package com.exampledigisphere.vitapetcare.auth.DTO;

import jakarta.validation.constraints.NotBlank;

public record ResetPasswordRequest(
  @NotBlank String email,
  @NotBlank String url
) {
}
