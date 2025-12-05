package com.exampledigisphere.vitapetcare.roles

import com.exampledigisphere.vitapetcare.roles.domain.Role
import com.exampledigisphere.vitapetcare.user.domain.User
import org.springframework.stereotype.Component

@Component
class PermissionFactory {

  fun getDefinedAuthorities(): Set<String> =
    setOf(
      User.Permissions.all(),
      Role.Permissions.all(),
    ).flatten().toSet()
}

