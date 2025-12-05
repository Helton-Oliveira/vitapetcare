package com.exampledigisphere.vitapetcare.config.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
  private val jwtTokenUtil: JwtTokenUtil,
  private val userDetailsService: UserDetailsService,
) : OncePerRequestFilter() {

  private val logger = LoggerFactory.getLogger(JwtAuthenticationFilter::class.java)

  override fun doFilterInternal(
    request: HttpServletRequest,
    response: HttpServletResponse,
    filterChain: FilterChain
  ) {

    val header = request.getHeader("Authorization")
    val token = header?.takeIf { it.startsWith("Bearer ") }?.substring(7)

    if (!token.isNullOrBlank()) {
      try {
        if (jwtTokenUtil.validate(token)) {
          val username = jwtTokenUtil.extractUsername(token)
          val userDetails = userDetailsService.loadUserByUsername(username)

          val authentication = UsernamePasswordAuthenticationToken(
            userDetails,
            null,
            userDetails.authorities
          )

          SecurityContextHolder.getContext().authentication = authentication

        }
      } catch (e: Exception) {
        logger.warn("Token inválido ou expirado. Requisição para ${request.requestURI}. Erro: ${e.message}")
        SecurityContextHolder.clearContext()
      }
    }

    filterChain.doFilter(request, response)
  }

  override fun shouldNotFilter(request: HttpServletRequest): Boolean {
    return (request.servletPath == "/api/users" && request.method == "POST") ||
      (request.servletPath == "/api/auth/login" && request.method == "POST")
  }
}
