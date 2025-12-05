package com.exampledigisphere.vitapetcare.auth.useCases

import com.exampledigisphere.vitapetcare.auth.DTO.LoginRequest
import com.exampledigisphere.vitapetcare.auth.DTO.LoginResponse
import com.exampledigisphere.vitapetcare.config.security.JwtTokenUtil
import com.exampledigisphere.vitapetcare.config.security.UserDetailsServiceImpl
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class Login(
  private val manager: AuthenticationManager,
  private val jwtTokenUtil: JwtTokenUtil,
  private val userDetailsServiceImpl: UserDetailsServiceImpl
) {

  fun execute(credentials: LoginRequest): Result<LoginResponse> =
    runCatching {
      val auth = manager.authenticate(
        UsernamePasswordAuthenticationToken(credentials.email, credentials.password)
      )
      SecurityContextHolder.getContext().authentication = auth

      val username = auth.name
      val userDetails = userDetailsServiceImpl.loadUserByUsername(username)

      val accessToken = jwtTokenUtil.generateAccessToken(
        userDetails.username,
        userDetails.authorities
      )

      val refreshToken = jwtTokenUtil.generateRefreshToken(
        userDetails.username
      )

      LoginResponse(
        accessToken = accessToken,
        refreshToken = refreshToken
      )
    }
}
