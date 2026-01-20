package com.exampledigisphere.vitapetcare.auth.roles;

import com.exampledigisphere.vitapetcare.admin.user.domain.User;
import com.exampledigisphere.vitapetcare.auth.authorities.Authorities;
import com.exampledigisphere.vitapetcare.config.root.Info;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Component
@Info(
  dev = Info.Dev.heltonOliveira,
  label = Info.Label.doc,
  date = "19/01/2026",
  description = "Mapeador de permissões baseado na Role do usuário"
)
public class RoleAuthorityMapper {

  @Info(
    dev = Info.Dev.heltonOliveira,
    label = Info.Label.doc,
    date = "19/01/2026",
    description = "Retorna o conjunto de permissões (authorities) para uma Role específica utilizando switch expression com yield"
  )
  public static Set<String> getAuthorities(Role role) {
    if (role == null) return Collections.emptySet();
    Set<String> authorities = new HashSet<>();

    return switch (role) {
      case ADMIN -> {
        authorities.addAll(Authorities.ALL());
        authorities.add("ROLE_ADMIN");
        yield Collections.unmodifiableSet(authorities);
      }

      case MANAGER -> {
        authorities.add("ROLE_MANAGER");
        authorities.add(User.Authority.USER_EDIT);
        authorities.add(User.Authority.USER_VIEW);
        authorities.add(User.Authority.USER_VIEW_LIST);
        authorities.addAll(Authorities.FILE_AUTHORITIES);
        authorities.addAll(Authorities.WORK_DAY_AUTHORITIES);
        yield Collections.unmodifiableSet(authorities);
      }

      case CLIENT -> {
        authorities.add("ROLE_CLIENT");
        authorities.add(User.Authority.USER_EDIT);
        authorities.add(User.Authority.USER_VIEW);
        authorities.addAll(Authorities.FILE_AUTHORITIES);
        yield Collections.unmodifiableSet(authorities);
      }

      case RECEPTIONIST -> {
        authorities.add("ROLE_RECEPTIONIST");
        authorities.add(User.Authority.USER_EDIT);
        authorities.add(User.Authority.USER_VIEW);
        authorities.addAll(Authorities.FILE_AUTHORITIES);
        yield Collections.unmodifiableSet(authorities);
      }

      case VETERINARIAN -> {
        authorities.add("ROLE_VETERINARIAN");
        authorities.add(User.Authority.USER_EDIT);
        authorities.add(User.Authority.USER_VIEW);
        authorities.addAll(Authorities.FILE_AUTHORITIES);
        yield Collections.unmodifiableSet(authorities);
      }

      case GROOMER -> {
        authorities.add(User.Authority.USER_EDIT);
        authorities.add(User.Authority.USER_VIEW);
        authorities.addAll(Authorities.FILE_AUTHORITIES);
        yield Collections.unmodifiableSet(authorities);
      }

      case NOT_INFORMED -> Collections.emptySet();
    };
  }
}
