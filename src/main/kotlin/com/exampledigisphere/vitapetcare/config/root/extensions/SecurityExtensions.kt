package com.exampledigisphere.vitapetcare.config.root.extensions

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails

fun getAuthenticatedUsername(): String =
  SecurityContextHolder
    .getContext()
    .authentication
    ?.takeIf { it.isAuthenticated }
    ?.principal
    ?.let { principal ->
      when (principal) {
        is UserDetails -> principal.username
        is String -> principal
        else -> principal.toString()
      }
    }
    ?: "system@email.com"
