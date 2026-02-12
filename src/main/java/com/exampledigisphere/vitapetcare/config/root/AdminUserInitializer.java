package com.exampledigisphere.vitapetcare.config.root;

import com.exampledigisphere.vitapetcare.admin.user.UserDTO;
import com.exampledigisphere.vitapetcare.admin.user.UserFactory;
import com.exampledigisphere.vitapetcare.admin.user.repository.UserRepository;
import com.exampledigisphere.vitapetcare.auth.roles.Role;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Collections;
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

    UserFactory.createFrom(
        new UserDTO(
          null,
          UUID.fromString("a5f1f06a-580d-4bf3-b1f0-6a580d1bf369").toString(),
          "Admin",
          adminEmail,
          "admin123",
          Role.ADMIN,
          Collections.emptySet(),
          Collections.emptySet(),
          true,
          true
        )
      )
      .map(userRepository::save);
  }
}

