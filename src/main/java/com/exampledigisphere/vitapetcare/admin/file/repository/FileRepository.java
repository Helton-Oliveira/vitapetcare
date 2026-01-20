package com.exampledigisphere.vitapetcare.admin.file.repository;

import com.exampledigisphere.vitapetcare.admin.file.domain.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.stream.Stream;

public interface FileRepository extends JpaRepository<File, Long> {

  @Query(nativeQuery = true, //
    value = """
      select f.* from fil_files f
          where f.user_id = :userId
          and f.active = true
      """
  )
  Stream<File> findAllByUserId(@Param("userId") Long userId);

}
