package com.exampledigisphere.vitapetcare.admin.file;

import com.exampledigisphere.vitapetcare.admin.file.repository.FileRepository;
import com.exampledigisphere.vitapetcare.admin.user.UserDTO;
import com.exampledigisphere.vitapetcare.config.root.Info;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

import static com.exampledigisphere.vitapetcare.config.root.Utils.initialize;
import static com.exampledigisphere.vitapetcare.config.root.Utils.transformToAssociationSet;
import static java.util.Collections.emptySet;

@Slf4j
@Service
@Transactional
@Info(
  dev = Info.Dev.heltonOliveira,
  label = Info.Label.feature,
  date = "06/02/2026",
  description = "Serviço para gestão semântica de arquivos"
)
public class FileService {

  private final FileRepository fileRepository;

  public FileService(final FileRepository fileRepository) {
    this.fileRepository = fileRepository;
  }

  @Info(
    dev = Info.Dev.heltonOliveira,
    label = Info.Label.feature,
    date = "06/02/2026",
    description = "Catalogar múltiplos arquivos vinculados a um usuário"
  )
  public void catalog(Set<FileDTO> files, UserDTO user) {
    log.info("Catalogando {} arquivos: {}", files.size());
    log.info("Usuario do arquivo: {}", user.getId());

    files.stream()
      .peek(f -> f.setUser(user))
      .forEach(f -> this.store(f, emptySet()));
  }

  @Info(
    dev = Info.Dev.heltonOliveira,
    label = Info.Label.feature,
    date = "06/02/2026",
    description = "Persiste um arquivo individualmente"
  )
  public Optional<FileDTO> store(@NonNull FileDTO input, Set<FileAssociations> associations) {
    log.info("Armazenando arquivo: {}", input);
    return FileFactory.createFrom(input)
      .map(fileRepository::save)
      .map(f -> f.applyAssociations(transformToAssociationSet(associations)))
      .map(FileFactory::toResponse);
  }

  @Info(
    dev = Info.Dev.heltonOliveira,
    label = Info.Label.feature,
    date = "06/02/2026",
    description = "Recupera um arquivo específico através de seu identificador"
  )
  @Transactional(readOnly = true)
  public Optional<FileDTO> retrieve(@NonNull Long id, Set<FileAssociations> associations) {
    log.info("Recuperando arquivo ID: {}", id);

    return fileRepository.findById(id)
      .map(f -> f.applyAssociations(transformToAssociationSet(associations)))
      .map(f -> initialize(f, associations))
      .map(FileFactory::toResponse);
  }

  @Info(
    dev = Info.Dev.heltonOliveira,
    label = Info.Label.feature,
    date = "06/02/2026",
    description = "Lista todos os arquivos registrados de forma paginada"
  )
  @Transactional(readOnly = true)
  public Page<FileDTO> list(@NonNull Pageable pageable) {
    log.info("Listando arquivos paginados");
    return fileRepository.findAll(pageable)
      .map(FileFactory::toResponse);
  }

  @Info(
    dev = Info.Dev.heltonOliveira,
    label = Info.Label.feature,
    date = "06/02/2026",
    description = "Descarta (desativa) um arquivo do sistema"
  )
  public void discard(@NonNull Long id) {
    log.info("Descartando arquivo ID: {}", id);
    fileRepository.findById(id)
      .ifPresent(f -> {
        f.disabled();
        fileRepository.save(f);
      });
  }
}
