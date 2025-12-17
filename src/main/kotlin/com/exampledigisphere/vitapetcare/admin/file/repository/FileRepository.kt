package com.exampledigisphere.vitapetcare.admin.file.repository

import com.exampledigisphere.vitapetcare.admin.file.domain.File
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FileRepository : JpaRepository<File, Long> {
}
