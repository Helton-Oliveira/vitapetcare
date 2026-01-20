package com.exampledigisphere.vitapetcare.auth.authorities;

import com.exampledigisphere.vitapetcare.admin.file.domain.File;
import com.exampledigisphere.vitapetcare.admin.user.domain.User;
import com.exampledigisphere.vitapetcare.admin.workDay.domain.WorkDay;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@NoArgsConstructor
@Component
public final class Authorities {

  public static final Set<String> USER_AUTHORITIES = Set.of(
    User.Authority.USER_CREATE,
    User.Authority.USER_EDIT,
    User.Authority.USER_VIEW,
    User.Authority.USER_DELETE,
    User.Authority.USER_VIEW_LIST
  );

  public static final Set<String> FILE_AUTHORITIES = Set.of(
    File.Authority.FILE_CREATE,
    File.Authority.FILE_EDIT,
    File.Authority.FILE_VIEW,
    File.Authority.FILE_DELETE,
    File.Authority.FILE_VIEW_LIST
  );

  public static final Set<String> WORK_DAY_AUTHORITIES = Set.of(
    WorkDay.Authority.WORK_DAY_CREATE,
    WorkDay.Authority.WORK_DAY_EDIT,
    WorkDay.Authority.WORK_DAY_VIEW,
    WorkDay.Authority.WORK_DAY_DELETE,
    WorkDay.Authority.WORK_DAY_VIEW_LIST
  );

  public static Set<String> ALL() {
    return Stream.of(
        USER_AUTHORITIES,
        FILE_AUTHORITIES,
        WORK_DAY_AUTHORITIES
      ).flatMap(Set::stream)
      .collect(Collectors.toSet());
  }
}
