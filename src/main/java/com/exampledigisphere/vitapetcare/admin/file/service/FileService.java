package com.exampledigisphere.vitapetcare.admin.file.service;

import com.exampledigisphere.vitapetcare.admin.file.domain.File;
import com.exampledigisphere.vitapetcare.admin.file.domain.FileInput;
import com.exampledigisphere.vitapetcare.admin.file.domain.FileMapper;
import com.exampledigisphere.vitapetcare.admin.file.domain.FileOutput;
import com.exampledigisphere.vitapetcare.admin.file.repository.FileRepository;
import com.exampledigisphere.vitapetcare.admin.user.domain.User;
import com.exampledigisphere.vitapetcare.config.root.Info;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.exampledigisphere.vitapetcare.config.root.OptionalUtils.peek;

@Service
@Transactional
@Slf4j
@Info(
  dev = Info.Dev.heltonOliveira,
  label = Info.Label.feature,
  date = "29/12/2025",
  description = "Serviço para gestão de arquivos com lógica funcional"
)
public class FileService {

  private final FileRepository fileRepository;
  private final FileMapper fileMapper;

  public FileService(final FileRepository fileRepository, final FileMapper fileMapper) {
    this.fileRepository = fileRepository;
    this.fileMapper = fileMapper;
  }

  @Info(
    dev = Info.Dev.heltonOliveira,
    label = Info.Label.feature,
    date = "29/12/2025",
    description = "Salva um arquivo"
  )
  public Optional<FileOutput> save(@NonNull FileInput input) {
    log.info("Iniciando persistência do arquivo: {}", input.name());

    return Optional.of(input)
      .map(fileMapper::toDomain)
      .map(fileRepository::save)
      .map(fileMapper::toOutput);
  }

  @Info(
    dev = Info.Dev.heltonOliveira,
    label = Info.Label.feature,
    date = "29/12/2025",
    description = "Busca todos os arquivos"
  )
  public List<FileOutput> findAll() {
    log.info("Buscando todos os arquivos");

    return fileRepository.findAll().stream()
      .map(fileMapper::toOutput)
      .toList();
  }

  @Info(
    dev = Info.Dev.heltonOliveira,
    label = Info.Label.feature,
    date = "29/12/2025",
    description = "Busca arquivo por ID"
  )
  public Optional<FileOutput> findById(@NonNull Long id) {
    log.info("Buscando arquivo por ID: {}", id);

    return fileRepository.findById(id)
      .map(fileMapper::toOutput);
  }

  @Info(
    dev = Info.Dev.heltonOliveira,
    label = Info.Label.feature,
    date = "29/12/2025",
    description = "Atualiza um arquivo existente"
  )
  public Optional<FileOutput> update(@NonNull FileInput input) {
    log.info("Atualizando arquivo ID: {}", input.id());

    return fileRepository.findById(input.id())
      .map(file -> {
        file.setName(input.name());
        file.setPath(input.path());
        file.setType(input.type());
        return file;
      })
      .map(fileRepository::save)
      .map(fileMapper::toOutput);
  }

  @Info(
    dev = Info.Dev.heltonOliveira,
    label = Info.Label.feature,
    date = "29/12/2025",
    description = "Desativa um arquivo (soft delete)"
  )
  public void disable(@NonNull Long id) {
    log.info("Desativando arquivo ID: {}", id);

    fileRepository.findById(id)
      .map(peek(File::disabled))
      .map(fileRepository::save);
  }

  @Info(
    dev = Info.Dev.heltonOliveira,
    label = Info.Label.feature,
    date = "29/12/2025",
    description = "Salva uma lista de arquivos associando-os a um usuário"
  )
  public void saveAllAndFlush(@NonNull Set<File> files, @NonNull User user) {
    log.info("Salvando lista de arquivos para o usuário: {}", user.getEmail());

    files.forEach(file -> {
      file.setUser(user);
      fileRepository.save(file);
    });
    fileRepository.flush();
  }
}
