package com.exampledigisphere.vitapetcare.config.security;

import com.exampledigisphere.vitapetcare.config.root.Info;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.util.Map;

@Component
@Info(
  dev = Info.Dev.heltonOliveira,
  label = Info.Label.doc,
  date = "29/12/2025",
  description = "Ponto de entrada customizado para erros de autenticação"
)
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

  private final ObjectMapper mapper = new ObjectMapper();

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setCharacterEncoding("UTF-8");

    Map<String, Object> errorResponse = Map.of(
      "status", HttpServletResponse.SC_UNAUTHORIZED,
      "error", "Unauthorized",
      "message", "Acesso Negado: O token de autenticação está expirado. Por favor, faça o login novamente.",
      "timestamp", Instant.now().toString()
    );

    response.getWriter().write(mapper.writeValueAsString(errorResponse));
  }
}
