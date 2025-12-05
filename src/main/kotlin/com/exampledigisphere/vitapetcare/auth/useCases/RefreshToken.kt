package com.exampledigisphere.vitapetcare.auth.useCases

import com.exampledigisphere.vitapetcare.auth.DTO.TokenRequest
import com.exampledigisphere.vitapetcare.auth.DTO.TokenResponse
import com.exampledigisphere.vitapetcare.config.security.JwtTokenUtil
import com.exampledigisphere.vitapetcare.config.security.UserDetailsServiceImpl
import org.springframework.stereotype.Service

@Service
class RefreshToken(
  private val jwtTokenUtil: JwtTokenUtil,
  private val userDetailsServiceImpl: UserDetailsServiceImpl
) {

  fun execute(refreshToken: TokenRequest): Result<TokenResponse> = runCatching {
    require(jwtTokenUtil.validate(refreshToken.refreshToken)) { "Invalid refresh token" }

    val username = jwtTokenUtil.extractUsername(refreshToken.refreshToken)
    val user = userDetailsServiceImpl.loadUserByUsername(username)

    val newAccessToken = jwtTokenUtil.generateAccessToken(
      username,
      user.authorities
    )

    TokenResponse(newAccessToken);
  }
}
