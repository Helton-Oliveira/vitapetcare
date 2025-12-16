package com.exampledigisphere.vitapetcare.user.useCases

import com.exampledigisphere.vitapetcare.file.useCases.SaveFile
import com.exampledigisphere.vitapetcare.user.domain.User
import com.exampledigisphere.vitapetcare.user.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.Collections.emptySet

@Service
@Transactional
class CreateUser(
  private val userRepository: UserRepository,
  private val passwordEncoder: PasswordEncoder,
  private val saveFile: SaveFile
) {

  fun execute(user: User): Result<User> = runCatching {
    val editedFiles = user.files
      .filterNotNull()
      .filter { it.wasEdited() }
      .toMutableSet()

    user.apply {
      password = passwordEncoder.encode(user.password)
      files = emptySet()
    }
      .let { userRepository.save(it) }
      .also { saveFile.execute(editedFiles, it) }
  }
}
