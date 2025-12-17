package com.exampledigisphere.vitapetcare.admin.workDay.resource

import com.exampledigisphere.vitapetcare.admin.workDay.domain.WorkDay
import com.exampledigisphere.vitapetcare.admin.workDay.useCases.DisableWorkDay
import com.exampledigisphere.vitapetcare.admin.workDay.useCases.GetAllWorkDay
import com.exampledigisphere.vitapetcare.admin.workDay.useCases.GetWorkDay
import com.exampledigisphere.vitapetcare.admin.workDay.useCases.SaveWorkDay
import jakarta.validation.ConstraintViolationException
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.util.UriComponentsBuilder

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/works_days")
class WorkDayResource(
  private val saveWorkDay: SaveWorkDay,
  private val getWorkDay: GetWorkDay,
  private val getAllWorkDay: GetAllWorkDay,
  private val disableWorkDay: DisableWorkDay
) {

  @PostMapping
  @PreAuthorize("hasAuthority('WORK_DAY_CREATE')")
  fun create(@RequestBody @Valid input: WorkDay, uriBuilder: UriComponentsBuilder): ResponseEntity<*> =
    saveWorkDay.execute(input)
      .fold(
        onFailure = { error ->
          error.printStackTrace()
          val status = when (error) {
            is ConstraintViolationException -> HttpStatus.CONFLICT
            else -> HttpStatus.INTERNAL_SERVER_ERROR
          }
          ResponseEntity.status(status).body(null)
        },
        onSuccess = { wk ->
          ResponseEntity
            .created(uriBuilder.path("/api/work_days/${wk.id}").build().toUri())
            .body(wk)
        }
      )

  @PutMapping
  @PreAuthorize("hasAuthority('WORK_DAY_EDIT')")
  fun update(@RequestBody @Valid input: WorkDay): ResponseEntity<*> =
    saveWorkDay.execute(input)
      .fold(
        onFailure = { error ->
          error.printStackTrace()
          ResponseEntity.status(HttpStatus.CONFLICT).body(error.message)
        },
        onSuccess = { wk ->
          ResponseEntity
            .status(HttpStatus.OK)
            .body(wk)
        }
      )

  @GetMapping("/{id}")
  @PreAuthorize("hasAuthority('WORK_DAY_VIEW')")
  fun getOne(@PathVariable id: Long): ResponseEntity<*> =
    getWorkDay.execute(id)
      .fold(
        onFailure = { err ->
          val status = when (err) {
            is HttpClientErrorException.Unauthorized -> HttpStatus.UNAUTHORIZED
            is IllegalArgumentException -> HttpStatus.CONFLICT
            else -> HttpStatus.INTERNAL_SERVER_ERROR
          }
          ResponseEntity.status(status).body(null)
        },
        onSuccess = { wk ->
          ResponseEntity.status(HttpStatus.OK).body(wk.also { wk?.loadShifts() })
        }
      )

  @GetMapping
  @PreAuthorize("hasAuthority('WORK_DAY_VIEW_LIST')")
  fun getAll(
    @PageableDefault(size = 10, sort = ["createdAt"], direction = Sort.Direction.DESC) pagination: Pageable
  ): ResponseEntity<Page<WorkDay>> =
    getAllWorkDay.execute(pagination)
      .fold(
        onFailure = { err ->
          val status = when (err) {
            is HttpClientErrorException.Unauthorized -> HttpStatus.UNAUTHORIZED
            is IllegalArgumentException -> HttpStatus.CONFLICT
            else -> HttpStatus.INTERNAL_SERVER_ERROR
          }

          ResponseEntity.status(status).body(null)
        },
        onSuccess = { wk -> ResponseEntity.status(HttpStatus.OK).body(wk) }
      )


  @DeleteMapping("/{id}")
  @PreAuthorize("hasAuthority('WORK_DAY_DELETE')")
  fun disable(@PathVariable id: Long): ResponseEntity<*> =
    disableWorkDay.execute(id)
      .fold(
        onFailure = { err ->
          err.printStackTrace()
          val status = when (err) {
            is HttpClientErrorException.Unauthorized -> HttpStatus.UNAUTHORIZED
            is IllegalArgumentException -> HttpStatus.CONFLICT
            else -> HttpStatus.INTERNAL_SERVER_ERROR
          }

          ResponseEntity.status(status).body(null)
        },
        onSuccess = { ResponseEntity.status(HttpStatus.OK).body(it) }
      )

}
