package com.exampledigisphere.vitapetcare.admin.file.domain;

import com.exampledigisphere.vitapetcare.config.root.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class FileMapper implements Mapper<FileInput, File, FileOutput> {

  @Override
  public File toDomain(FileInput input) {
    if (input == null) {
      return null;
    }
    File file = new File();
    file.setId(input.id());
    file.setName(input.name());
    file.setPath(input.path());
    file.setType(input.type());
    // User deve ser associado no service
    return file;
  }

  @Override
  public FileOutput toOutput(File domain) {
    if (domain == null) {
      return null;
    }
    return new FileOutput(
      domain.getId(),
      domain.getName(),
      domain.getPath(),
      domain.getType(),
      domain.getUser() != null ? domain.getUser().getId() : null,
      domain.isActive()
    );
  }
}
