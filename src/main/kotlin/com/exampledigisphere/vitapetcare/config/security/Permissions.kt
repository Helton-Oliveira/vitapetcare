package com.exampledigisphere.vitapetcare.config.security

interface Permissions {
  var resource: String

  fun all(): Set<String> =
    Operation.entries.map { op -> "${resource}_${op.name}" }.toSet()

  fun of(vararg ops: Operation): Set<String> =
    ops.map { op -> "${resource}_${op.name}" }.toSet()
}

