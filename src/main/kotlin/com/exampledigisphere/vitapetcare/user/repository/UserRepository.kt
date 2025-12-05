package com.exampledigisphere.vitapetcare.user.repository

import com.exampledigisphere.vitapetcare.user.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {

  @Query(
    nativeQuery = true, //
    value =
      " select * from usr_users u   " +//
        "   where u.email = :email  " +//
        "   and u.active = true     "
  )
  fun findByUsername(@Param("email") email: String): User?

  @Query(
    nativeQuery = true, //
    value =
      " select * from usr_users u       " +//
        "   where u.reset_key = :resetKey " +//
        "   and u.active = true           "
  )
  fun findByResetKey(@Param("resetKey") resetKey: String): User?

}
