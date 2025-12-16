package com.exampledigisphere.vitapetcare.roles

import com.exampledigisphere.vitapetcare.config.security.Operation
import com.exampledigisphere.vitapetcare.file.domain.File
import com.exampledigisphere.vitapetcare.user.domain.User

enum class Role(val permissions: Set<String>) {
  ADMIN(
    User.UserPermissions.all() + File.FilePermissions.all(),
  ),

  MANAGER(
    User.UserPermissions.all() + File.FilePermissions.all()
  ),

  CLIENT(
    User.UserPermissions.of(Operation.EDIT, Operation.VIEW)
      + File.FilePermissions.of(Operation.EDIT, Operation.VIEW)
  ),

  RECEPTIONIST(
    User.UserPermissions.of(Operation.EDIT, Operation.VIEW)
      + File.FilePermissions.of(Operation.EDIT, Operation.VIEW)
  ),

  VETERINARIAN(
    User.UserPermissions.of(Operation.EDIT, Operation.VIEW)
      + File.FilePermissions.of(Operation.EDIT, Operation.VIEW)
  ),

  GROOMER(
    User.UserPermissions.of(Operation.EDIT, Operation.VIEW)
      + File.FilePermissions.of(Operation.EDIT, Operation.VIEW)
  ),

  NOT_INFORMED(emptySet()),
}
