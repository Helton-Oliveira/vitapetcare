package com.exampledigisphere.vitapetcare.file.useCases

import com.exampledigisphere.vitapetcare.file.domain.File
import com.exampledigisphere.vitapetcare.file.repository.FileRepository
import com.exampledigisphere.vitapetcare.user.domain.User
import org.springframework.stereotype.Service

@Service
class SaveFile(
  private val fileRepository: FileRepository,
) {

  fun execute(files: MutableSet<File>, user: User) =
    files.map { file -> file.apply { this.user = user } }
      .map(fileRepository::save)

}
