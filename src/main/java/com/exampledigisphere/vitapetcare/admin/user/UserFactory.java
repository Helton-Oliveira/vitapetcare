package com.exampledigisphere.vitapetcare.admin.user;

import com.exampledigisphere.vitapetcare.admin.file.FileDTO;
import com.exampledigisphere.vitapetcare.admin.file.FileFactory;
import com.exampledigisphere.vitapetcare.admin.user.domain.User;
import com.exampledigisphere.vitapetcare.admin.user.domain.UserAssociations;
import com.exampledigisphere.vitapetcare.admin.workDay.WorkDayDTO;
import com.exampledigisphere.vitapetcare.admin.workDay.WorkDayFactory;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

import static com.exampledigisphere.vitapetcare.config.root.Utils.mapIfRequested;

@Slf4j
@Service
public class UserFactory {

  public static Optional<User> createFrom(UserDTO input) {
    log.info("createFrom, {}", input);

    return Optional.of(
      new User(
        input.getId(),
        input.getName(),
        input.getEmail(),
        input.getPassword(),
        input.getRole(),
        input.getEdited(),
        input.getActive()
      )
    );
  }

  public static UserDTO toResponse(User domain) {
    log.info("toResponse, {}", domain.getId());

    return new UserDTO(
      domain.getId(),
      domain.getUuid(),
      domain.getName(),
      domain.getEmail(),
      domain.getPassword(),
      domain.getRole(),
      mapIfRequested(domain.hasAssociation(UserAssociations.FILES.name()), () -> toFiles(domain)),
      mapIfRequested(domain.hasAssociation(UserAssociations.WORK_DAYS.name()), () -> toWorkDays(domain)),
      domain.isEdited(),
      domain.isActive()
    );
  }

  private static Set<FileDTO> toFiles(User user) {
    if (user.getFiles() == null || !Hibernate.isInitialized(user.getFiles())) return null;
    return FileFactory.toResponseList(user.getFiles());
  }

  private static Set<WorkDayDTO> toWorkDays(User user) {
    if (user.getWorkDays() == null || !Hibernate.isInitialized(user.getWorkDays())) return null;
    return WorkDayFactory.toResponseList(user.getWorkDays());
  }
}
