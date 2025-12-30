package com.exampledigisphere.vitapetcare.admin.user.domain;

import com.exampledigisphere.vitapetcare.auth.roles.RoleAuthorityMapper;
import com.exampledigisphere.vitapetcare.config.root.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements Mapper<UserInput, User, UserOutput> {

  @Override
  public User toDomain(UserInput input) {
    if (input == null) {
      return null;
    }
    User user = new User();
    user.setId(input.id());
    user.setName(input.name());
    user.setEmail(input.email());
    user.setPassword(input.password());
    user.setFiles(input.files());
    user.setRole(input.role());
    // Nota: Roles precisam ser carregadas via repository no service,
    // ou podemos apenas setar o ID se o JPA suportar referências.
    // Por simplicidade aqui, não manipulamos coleções complexas no mapper puro.
    return user;
  }

  @Override
  public UserOutput toOutput(User domain) {
    if (domain == null) {
      return null;
    }
    return new UserOutput(
      domain.getId(),
      domain.getName(),
      domain.getEmail(),
      domain.getRole(),
      domain.isActive(),
      RoleAuthorityMapper.getAuthorities(domain.getRole())
    );
  }
}
