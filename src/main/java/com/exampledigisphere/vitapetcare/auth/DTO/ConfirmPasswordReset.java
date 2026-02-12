package com.exampledigisphere.vitapetcare.auth.DTO;

import jakarta.validation.constraints.NotBlank;

public record ConfirmPasswordReset(
  @NotBlank String newPassword,
  @NotBlank String resetCode
) {
}
