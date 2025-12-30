package com.exampledigisphere.vitapetcare.admin.role;

import com.exampledigisphere.vitapetcare.auth.roles.ERole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(ERole name);
}
