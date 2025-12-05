package com.exampledigisphere.vitapetcare.roles.resource

import com.exampledigisphere.vitapetcare.roles.domain.Role
import com.exampledigisphere.vitapetcare.roles.useCase.CreateRole
import com.exampledigisphere.vitapetcare.roles.useCase.GetAllPermissions
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("/api/roles")
class RoleResource(
  private val createRole: CreateRole,
  private val getAllPermissions: GetAllPermissions,
) {

  @PostMapping
  fun create(@RequestBody @Valid role: Role, uriBuilder: UriComponentsBuilder): ResponseEntity<*> =
    createRole.execute(role)
      .fold(
        onFailure = { role ->
          role.printStackTrace()
          ResponseEntity.status(HttpStatus.CONFLICT).body(role)
        },
        onSuccess = { role ->
          ResponseEntity.created(uriBuilder.path("/api/users/${role.id}").build().toUri())
            .body(
              role.also { role.loadUsers() }
            )
        }
      )

  @GetMapping("/permissions")
  fun getAllPermissions(): ResponseEntity<*> =
    getAllPermissions.execute()
      .fold(
        onFailure = { permissions ->
          permissions.printStackTrace()
          ResponseEntity.status(HttpStatus.CONFLICT).body(permissions)
        },
        onSuccess = { permissions -> ResponseEntity.status(HttpStatus.OK).body(permissions) }
      )
}
