package com.exampledigisphere.vitapetcare.config.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.GrantedAuthority
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtTokenUtil {

  @Value("\${jwt.secret}")
  private lateinit var secret: String

  private val accessExpiration: Long = 1000L * 60 * 60 * 24
  private val refreshExpiration: Long = 1000L * 60 * 60 * 24 * 30

  private fun getSignInKey(): SecretKey {
    val keyBytes = Decoders.BASE64.decode(secret)
    return Keys.hmacShaKeyFor(keyBytes)
  }

  fun generateAccessToken(username: String, roles: Collection<GrantedAuthority>): String {
    return Jwts.builder()
      .subject(username)
      .claim("roles", roles.map { it.authority })
      .expiration(Date(System.currentTimeMillis() + accessExpiration))
      .signWith(getSignInKey())
      .compact()
  }

  fun generateRefreshToken(username: String): String {
    return Jwts.builder()
      .subject(username)
      .issuedAt(Date())
      .expiration(Date(System.currentTimeMillis() + refreshExpiration))
      .signWith(getSignInKey())
      .compact()
  }

  fun validate(token: String?): Boolean {
    return try {
      Jwts.parser()
        .verifyWith(getSignInKey())
        .build()
        .parseSignedClaims(token)
      true
    } catch (e: Exception) {
      false
    }
  }

  fun extractUsername(token: String): String {
    return Jwts.parser()
      .verifyWith(getSignInKey())
      .build()
      .parseSignedClaims(token)
      .payload
      .subject
  }

}
