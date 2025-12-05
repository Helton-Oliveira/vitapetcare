package com.exampledigisphere.vitapetcare.auth.resource

import com.exampledigisphere.vitapetcare.auth.DTO.LoginRequest
import com.exampledigisphere.vitapetcare.auth.DTO.TokenRequest
import com.exampledigisphere.vitapetcare.auth.useCases.GetCurrentAccount
import com.exampledigisphere.vitapetcare.auth.useCases.Login
import com.exampledigisphere.vitapetcare.auth.useCases.RefreshToken
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthResource(
  private val login: Login,
  private val refreshToken: RefreshToken,
  private val getCurrentAccount: GetCurrentAccount
) {

  @PostMapping("/login")
  fun login(@RequestBody credentials: LoginRequest): ResponseEntity<*> {
    login.execute(credentials)
      .fold(
        onFailure = { err ->
          err.printStackTrace()
          return ResponseEntity.status(HttpStatus.CONFLICT).body(err.message)
        },
        onSuccess = { return ResponseEntity.ok().body(it) }
      )
  }

  @PostMapping("/refresh")
  fun refresh(@RequestBody request: TokenRequest): ResponseEntity<*> {
    refreshToken.execute(request)
      .fold(
        onFailure = { err ->
          err.printStackTrace()
          return ResponseEntity.status(HttpStatus.CONFLICT).body(err.message)
        },
        onSuccess = { return ResponseEntity.ok().body(it) }
      )
  }

  @GetMapping("/current-account")
  fun getCurrentUser(): ResponseEntity<*> =
    getCurrentAccount.execute()
      .fold(
        onFailure = { err ->
          err.printStackTrace()
          ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err.message)
        },
        onSuccess = { ResponseEntity.status(HttpStatus.OK).body(it) }
      )
}
