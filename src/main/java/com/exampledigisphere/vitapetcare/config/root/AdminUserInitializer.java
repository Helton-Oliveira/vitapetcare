package com.exampledigisphere.vitapetcare.config.root;

import com.exampledigisphere.vitapetcare.admin.role.Role;
import com.exampledigisphere.vitapetcare.admin.role.RoleRepository;
import com.exampledigisphere.vitapetcare.admin.user.domain.User;
import com.exampledigisphere.vitapetcare.admin.user.repository.UserRepository;
import com.exampledigisphere.vitapetcare.auth.roles.ERole;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Component
@Profile("dev")
public class AdminUserInitializer implements ApplicationRunner {

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;

  public AdminUserInitializer(final UserRepository userRepository,
                              final RoleRepository roleRepository,
                              final PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public void run(ApplicationArguments args) {
    createAdminIfNotExists();
  }

  private void createAdminIfNotExists() {
    final var adminEmail = "admin@vitapetcare.com";

    if (userRepository.existsByEmail(adminEmail)) return;

    final var adminRole = roleRepository.findByName(ERole.ADMIN)
      .orElseGet(() -> {
        Role role = new Role();
        role.setName(ERole.ADMIN);
        return roleRepository.save(role);
      });

    final var admin = new User();
    admin.setUuid(UUID.fromString("a5f1f06a-580d-4bf3-b1f0-6a580d1bf369").toString());
    admin.setName("Admin");
    admin.setEmail(adminEmail);
    admin.setPassword(passwordEncoder.encode("admin123"));
    admin.setRoles(Set.of(adminRole));
    admin.setActive(true);
    admin.setFiles(new HashSet());
    admin.setWorkDays(new HashSet());

    userRepository.save(admin);
  }
}

