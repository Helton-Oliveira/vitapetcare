package com.exampledigisphere.vitapetcare.user.resource

import com.exampledigisphere.vitapetcare.user.domain.User
import com.exampledigisphere.vitapetcare.user.useCases.CreateUser
import com.exampledigisphere.vitapetcare.user.useCases.GetAll
import com.exampledigisphere.vitapetcare.user.useCases.GetUser
import jakarta.validation.ConstraintViolationException
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("/api/users")
class UserResource(
  private val createUser: CreateUser,
  private val getUser: GetUser,
  private val getAll: GetAll,
) {

  @PostMapping
  fun create(@RequestBody @Valid input: User, uriBuilder: UriComponentsBuilder): ResponseEntity<*> =
    createUser.execute(input)
      .fold(
        onFailure = { error ->
          error.printStackTrace()
          val status = when (error) {
            is ConstraintViolationException -> HttpStatus.CONFLICT
            else -> HttpStatus.INTERNAL_SERVER_ERROR
          }
          ResponseEntity.status(status).body(null)
        },
        onSuccess = { usr ->
          ResponseEntity
            .created(uriBuilder.path("/api/users/${usr.id}").build().toUri())
            .body(usr.also { it.loadRole() })
        }
      )

  @GetMapping("/{id}")
  fun getOne(@PathVariable id: Long): ResponseEntity<*> =
    getUser.execute(id)
      .fold(
        onFailure = { err ->
          val status = when (err) {
            is HttpClientErrorException.Unauthorized -> HttpStatus.UNAUTHORIZED
            is IllegalArgumentException -> HttpStatus.CONFLICT
            else -> HttpStatus.INTERNAL_SERVER_ERROR
          }
          ResponseEntity.status(status).body(null)
        },
        onSuccess = { usr ->
          ResponseEntity.status(HttpStatus.OK).body(usr.also { usr?.loadRole() })
        }
      )

  @GetMapping
  fun getAll(
    @PageableDefault(size = 10, sort = ["createdAt"], direction = Sort.Direction.DESC) pagination: Pageable
  ): ResponseEntity<Page<User>> =
    getAll.execute(pagination)
      .fold(
        onFailure = { err ->
          val status = when (err) {
            is HttpClientErrorException.Unauthorized -> HttpStatus.UNAUTHORIZED
            is IllegalArgumentException -> HttpStatus.CONFLICT
            else -> HttpStatus.INTERNAL_SERVER_ERROR
          }

          ResponseEntity.status(status).body(null)
        },
        onSuccess = { usr -> ResponseEntity.status(HttpStatus.OK).body(usr) }
      )

}
