package com.exampledigisphere.vitapetcare.admin.file.repository;

import com.exampledigisphere.vitapetcare.admin.file.domain.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {
}
