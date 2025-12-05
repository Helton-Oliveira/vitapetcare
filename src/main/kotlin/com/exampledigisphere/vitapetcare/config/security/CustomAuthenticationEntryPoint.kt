package com.exampledigisphere.vitapetcare.config.security

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import java.time.Instant

@Component
class CustomAuthenticationEntryPoint : AuthenticationEntryPoint {

  private val mapper = ObjectMapper();

  override fun commence(
    request: HttpServletRequest?,
    response: HttpServletResponse?,
    authException: AuthenticationException?
  ) {
    response?.status = HttpServletResponse.SC_UNAUTHORIZED // 401

    response?.contentType = MediaType.APPLICATION_JSON_VALUE
    response?.characterEncoding = "UTF-8"

    val errorResponse = mapOf(
      "status" to HttpServletResponse.SC_UNAUTHORIZED,
      "error" to "Unauthorized",
      "message" to "Acesso Negado: O token de autenticação está expirado. Por favor, faça o login novamente.",
      "timestamp" to Instant.now().toString()
    )

    response?.writer?.write(mapper.writeValueAsString(errorResponse))
  }
}
