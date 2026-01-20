package com.exampledigisphere.vitapetcare.config.root;

import com.exampledigisphere.vitapetcare.admin.user.domain.User;
import com.exampledigisphere.vitapetcare.admin.user.repository.UserRepository;
import com.exampledigisphere.vitapetcare.auth.roles.Role;
import com.exampledigisphere.vitapetcare.auth.roles.RoleAuthorityMapper;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.UUID;

@Component
@Profile("dev")
public class AdminUserInitializer implements ApplicationRunner {

  private final UserRepository userRepository;

  public AdminUserInitializer(final UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public void run(ApplicationArguments args) {
    createAdminIfNotExists();
  }

  private void createAdminIfNotExists() {
    final var adminEmail = "admin@vitapetcare.com";

    if (userRepository.existsByEmail(adminEmail)) return;

    final var admin = new User();
    admin.setUuid(UUID.fromString("a5f1f06a-580d-4bf3-b1f0-6a580d1bf369").toString());
    admin.setName("Admin");
    admin.setEmail(adminEmail);
    admin.setPassword("admin123").encryptPassword();
    admin.setRole(Role.ADMIN);
    admin.setFiles(new HashSet<>());
    admin.setWorkDays(new HashSet<>());
    admin.setActive(true);
    admin.setAuthorities(RoleAuthorityMapper.getAuthorities(admin.getRole()));
    userRepository.save(admin);
  }
}

