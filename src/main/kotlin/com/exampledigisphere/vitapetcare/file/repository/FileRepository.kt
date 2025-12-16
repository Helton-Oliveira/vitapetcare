package com.exampledigisphere.vitapetcare.file.repository

import com.exampledigisphere.vitapetcare.file.domain.File
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FileRepository : JpaRepository<File, Long> {
}
