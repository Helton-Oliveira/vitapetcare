package com.exampledigisphere.vitapetcare.auth.DTO

import jakarta.validation.constraints.NotBlank

data class LoginRequest(
  @field:NotBlank val email: String,
  @field:NotBlank val password: String
)

data class TokenRequest(
  @field:NotBlank val refreshToken: String,
)

data class LoginResponse(
  val refreshToken: String,
  val accessToken: String
)

data class TokenResponse(
  val accessToken: String
)

data class PermissionsResponse(
  val id: Int,
  val name: String,
)
