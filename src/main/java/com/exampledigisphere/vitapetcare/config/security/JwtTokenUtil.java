package com.exampledigisphere.vitapetcare.config.security;

import com.exampledigisphere.vitapetcare.config.root.Info;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;

@Component
@Info(
  dev = Info.Dev.heltonOliveira,
  label = Info.Label.doc,
  date = "29/12/2025",
  description = "Utilitário para manipulação de tokens JWT"
)
public class JwtTokenUtil {

  @Value("${jwt.secret}")
  private String secret;

  private final long accessExpiration = 1000L * 60 * 60 * 24;
  private final long refreshExpiration = 1000L * 60 * 60 * 24 * 30;

  private SecretKey getSignInKey() {
    byte[] keyBytes = Decoders.BASE64.decode(secret);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  public String generateAccessToken(String username, Collection<? extends GrantedAuthority> roles) {
    return Jwts.builder()
      .subject(username)
      .claim("roles", roles.stream().map(GrantedAuthority::getAuthority).toList())
      .expiration(new Date(System.currentTimeMillis() + accessExpiration))
      .signWith(getSignInKey())
      .compact();
  }

  public String generateRefreshToken(String username) {
    return Jwts.builder()
      .subject(username)
      .issuedAt(new Date())
      .expiration(new Date(System.currentTimeMillis() + refreshExpiration))
      .signWith(getSignInKey())
      .compact();
  }

  public boolean validate(String token) {
    try {
      Jwts.parser()
        .verifyWith(getSignInKey())
        .build()
        .parseSignedClaims(token);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public String extractUsername(String token) {
    return Jwts.parser()
      .verifyWith(getSignInKey())
      .build()
      .parseSignedClaims(token)
      .getPayload()
      .getSubject();
  }
}
