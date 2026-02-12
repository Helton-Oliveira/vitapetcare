package com.exampledigisphere.vitapetcare.admin.user.repository;

import com.exampledigisphere.vitapetcare.admin.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  boolean existsByEmail(String email);

  @Query(
    nativeQuery = true, //
    value =
      """
          select u.* from usr_users u
          left join fil_files f on f.user_id = u.id
          where u.email = :email
            and u.active = true
        """
  )
  Optional<User> findByUsername(@Param("email") String email);

  @Query(
    nativeQuery = true, //
    value =
      """
          select u.* from usr_users u
          left join fil_files f on f.user_id = u.id
          where u.id = :id
            and u.active = true
        """
  )
  Optional<User> findFullById(@Param("id") Long id);

  @Query(nativeQuery = true, //
    value =
      """
        select u.* from usr_users u
         where u.reset_code = :resetCode
          and u.active = true
        """
  )
  Optional<User> findByResetCode(@Param("resetCode") String resetCode);

}
