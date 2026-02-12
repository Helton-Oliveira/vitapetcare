package com.exampledigisphere.vitapetcare.admin.file;

import com.exampledigisphere.vitapetcare.admin.file.domain.File;
import com.exampledigisphere.vitapetcare.admin.user.UserDTO;
import com.exampledigisphere.vitapetcare.admin.user.UserFactory;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.exampledigisphere.vitapetcare.config.root.Utils.mapIfRequested;

@Slf4j
@Service
public class FileFactory {

  public static Optional<File> createFrom(FileDTO input) {
    log.info("createFrom, {}", input);

    File file = new File();
    file.setId(input.getId());
    file.setName(input.getName());
    file.setPath(input.getPath());
    file.setType(input.getType());
    file.setUser(UserFactory.createFrom(input.getUser()).orElse(null));
    file.setEdited(input.getEdited());
    file.setActive(input.getActive());

    return Optional.of(file);
  }

  public static FileDTO toResponse(File domain) {
    log.info("toResponse, {}", domain.getId());

    return new FileDTO(
      domain.getId(),
      domain.getUuid(),
      domain.getName(),
      domain.getPath(),
      domain.getType(),
      mapIfRequested(domain.hasAssociation(FileAssociations.USER.name()), () -> toUserDTO(domain)),
      domain.isEdited(),
      domain.isActive()
    );
  }

  public static Set<FileDTO> toResponseList(Set<File> list) {
    log.info("Iniciando mapeamento de lista de arquivos...");

    return list.stream().map(FileFactory::toResponse).collect(Collectors.toSet());
  }

  private static UserDTO toUserDTO(File file) {
    if (file.getUser() == null || !Hibernate.isInitialized(file.getUser())) return null;
    return UserFactory.toResponse(file.getUser());
  }
}
