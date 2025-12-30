package com.exampledigisphere.vitapetcare.auth.roles;

import com.exampledigisphere.vitapetcare.admin.user.domain.User;
import com.exampledigisphere.vitapetcare.auth.authorities.Authorities;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@NoArgsConstructor
@Component
public class RoleAuthorityMapper {

  public static Set<String> getAuthorities(Role role) {
    Set<String> authorities = new HashSet<>();

    return switch (role) {
      case ADMIN -> Stream.concat(
        Authorities.ALL().stream(),
        Stream.of("ROLE_ADMIN")
      ).collect(Collectors.toUnmodifiableSet());

      case MANAGER -> Stream.concat(
        Stream.of(
          User.Authority.USER_EDIT,
          User.Authority.USER_VIEW,
          User.Authority.USER_VIEW_LIST
        ),
        Authorities.FILE_AUTHORITIES.stream()
      ).collect(Collectors.toUnmodifiableSet());

      case CLIENT -> Stream.concat(
        Stream.of(
          User.Authority.USER_EDIT,
          User.Authority.USER_VIEW
        ),
        Authorities.FILE_AUTHORITIES.stream()
      ).collect(Collectors.toUnmodifiableSet());


      case RECEPTIONIST -> Stream.concat(
        Stream.of(
          User.Authority.USER_EDIT,
          User.Authority.USER_VIEW
        ),
        Authorities.FILE_AUTHORITIES.stream()
      ).collect(Collectors.toUnmodifiableSet());

      case VETERINARIAN -> Stream.concat(
        Stream.of(
          User.Authority.USER_EDIT,
          User.Authority.USER_VIEW
        ),
        Authorities.FILE_AUTHORITIES.stream()
      ).collect(Collectors.toUnmodifiableSet());

      case GROOMER -> Stream.concat(
        Stream.of(
          User.Authority.USER_EDIT,
          User.Authority.USER_VIEW
        ),
        Authorities.FILE_AUTHORITIES.stream()
      ).collect(Collectors.toUnmodifiableSet());


      case NOT_INFORMED -> Collections.emptySet();
    };
  }
}
