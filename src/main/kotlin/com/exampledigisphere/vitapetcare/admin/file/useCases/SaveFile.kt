package com.exampledigisphere.vitapetcare.admin.file.useCases

import com.exampledigisphere.vitapetcare.admin.file.domain.File
import com.exampledigisphere.vitapetcare.admin.file.repository.FileRepository
import com.exampledigisphere.vitapetcare.admin.user.domain.User
import org.springframework.stereotype.Service

@Service
class SaveFile(
  private val fileRepository: FileRepository,
) {

  fun execute(files: MutableSet<File>, user: User) =
    files.map { file -> file.apply { this.user = user } }
      .map(fileRepository::save)

}
