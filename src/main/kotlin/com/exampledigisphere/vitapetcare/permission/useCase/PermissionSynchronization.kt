package com.exampledigisphere.vitapetcare.roles.service

import com.exampledigisphere.vitapetcare.permission.domain.Permission
import com.exampledigisphere.vitapetcare.permission.repository.PermissionRepository
import com.exampledigisphere.vitapetcare.roles.PermissionFactory
import jakarta.annotation.PostConstruct
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PermissionSynchronization(
  private val permissionFactory: PermissionFactory,
  private val permissionRepository: PermissionRepository
) {
  private val logger = LoggerFactory.getLogger(javaClass)

  @PostConstruct
  @Transactional
  fun syncPermissions() {
    val codePermissions = permissionFactory.getDefinedAuthorities()
    val dbPermissions = permissionRepository.findAll().associateBy { it.name } // Mapeia por nome

    val permissionsToCreate = codePermissions.filter { it !in dbPermissions.keys }

    logger.info("==============================================")
    logger.info("🚀 Sincronização de Permissões Iniciada.")
    logger.info("Total de Permissões Definidas no Código: {}", codePermissions.size)
    logger.info("Total de Permissões Atualmente no Banco: {}", dbPermissions.size)

    if (permissionsToCreate.isNotEmpty()) {
      logger.warn("⚠️ {} novas permissões encontradas. Iniciando persistência.", permissionsToCreate.size)

      permissionsToCreate.forEach { name ->
        val newPermission = Permission().apply { this.name = name }
        permissionRepository.save(newPermission)
        logger.info("   -> SALVA: {}", name)
      }
    } else {
      logger.info("✅ Nenhuma nova permissão para salvar. Banco de dados sincronizado.")
    }

    logger.info("==============================================")
  }
}
