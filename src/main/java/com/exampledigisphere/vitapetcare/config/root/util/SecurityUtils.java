package com.exampledigisphere.vitapetcare.config.root.util;

import com.exampledigisphere.vitapetcare.config.root.Info;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

@Info(
  dev = Info.Dev.heltonOliveira,
  label = Info.Label.doc,
  date = "29/12/2025",
  description = "Utilidades para Segurança, migrado de SecurityExtensions.kt"
)
public class SecurityUtils {

  public static String getAuthenticatedUsername() {
    return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
      .filter(Authentication::isAuthenticated)
      .map(Authentication::getPrincipal)
      .map(principal -> {
        if (principal instanceof UserDetails userDetails) {
          return userDetails.getUsername();
        } else if (principal instanceof String s) {
          return s;
        } else {
          return principal.toString();
        }
      })
      .orElse("system@email.com");
  }
}
