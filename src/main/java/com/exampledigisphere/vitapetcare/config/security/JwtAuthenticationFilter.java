package com.exampledigisphere.vitapetcare.config.security;

import com.exampledigisphere.vitapetcare.config.root.Info;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
@Info(
  dev = Info.Dev.heltonOliveira,
  label = Info.Label.doc,
  date = "29/12/2025",
  description = "Filtro de autenticação JWT"
)
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtTokenUtil jwtTokenUtil;
  private final UserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    String header = request.getHeader("Authorization");
    String token = Optional.ofNullable(header)
      .filter(h -> h.startsWith("Bearer "))
      .map(h -> h.substring(7))
      .orElse(null);

    if (token != null && !token.isBlank()) {
      try {
        if (jwtTokenUtil.validate(token)) {
          String username = jwtTokenUtil.extractUsername(token);
          UserDetails userDetails = userDetailsService.loadUserByUsername(username);

          UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
            userDetails,
            null,
            userDetails.getAuthorities()
          );

          SecurityContextHolder.getContext().setAuthentication(authentication);
        }
      } catch (Exception e) {
        log.warn("Token inválido ou expirado. Requisição para {}. Erro: {}", request.getRequestURI(), e.getMessage());
        SecurityContextHolder.clearContext();
      }
    }

    filterChain.doFilter(request, response);
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) {
    return "/api/auth/login".equals(request.getServletPath()) && "POST".equalsIgnoreCase(request.getMethod());
  }
}
