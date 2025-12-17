package com.exampledigisphere.vitapetcare.auth.roles

import com.exampledigisphere.vitapetcare.admin.file.domain.File
import com.exampledigisphere.vitapetcare.admin.timePeriod.domain.TimePeriod
import com.exampledigisphere.vitapetcare.admin.user.domain.User
import com.exampledigisphere.vitapetcare.admin.workDay.domain.WorkDay
import com.exampledigisphere.vitapetcare.config.security.Operation

enum class Role(val permissions: Set<String>) {
  ADMIN(
    User.UserPermissions.all() + //
      File.FilePermissions.all() + //
      WorkDay.WorkDayPermissions.all() + //
      TimePeriod.TimePeriodPermissions.all()
  ),

  MANAGER(
    User.UserPermissions.all() + //
      File.FilePermissions.all() + //
      WorkDay.WorkDayPermissions.all() + //
      TimePeriod.TimePeriodPermissions.all()
  ),

  CLIENT(
    User.UserPermissions.of(Operation.EDIT, Operation.VIEW) + //
      File.FilePermissions.of(Operation.EDIT, Operation.VIEW)
  ),

  RECEPTIONIST(
    User.UserPermissions.of(Operation.EDIT, Operation.VIEW) + //
      File.FilePermissions.of(Operation.EDIT, Operation.VIEW)
  ),

  VETERINARIAN(
    User.UserPermissions.of(Operation.EDIT, Operation.VIEW) + //
      File.FilePermissions.of(Operation.EDIT, Operation.VIEW)
  ),

  GROOMER(
    User.UserPermissions.of(Operation.EDIT, Operation.VIEW) + //
      File.FilePermissions.of(Operation.EDIT, Operation.VIEW)
  ),

  NOT_INFORMED(emptySet()),
}
