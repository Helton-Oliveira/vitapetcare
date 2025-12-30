package com.exampledigisphere.vitapetcare.admin.file.domain;

public record FileOutput(
  Long id,
  String name,
  String path,
  FileType type,
  Long userId,
  boolean active
) {
}
