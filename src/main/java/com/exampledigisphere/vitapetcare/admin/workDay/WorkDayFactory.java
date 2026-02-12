package com.exampledigisphere.vitapetcare.admin.workDay;

import com.exampledigisphere.vitapetcare.admin.timePeriod.TimePeriodDTO;
import com.exampledigisphere.vitapetcare.admin.timePeriod.TimePeriodFactory;
import com.exampledigisphere.vitapetcare.admin.user.UserDTO;
import com.exampledigisphere.vitapetcare.admin.user.UserFactory;
import com.exampledigisphere.vitapetcare.admin.workDay.domain.WorkDay;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

import static com.exampledigisphere.vitapetcare.config.root.Utils.mapIfRequested;


@Slf4j
@Service
public class WorkDayFactory {

  public static WorkDay createFrom(WorkDayDTO input) {
    log.info("createFrom, {}", input);

    WorkDay workDay = new WorkDay();
    workDay.setId(input.getId());
    workDay.setDayOfWeek(input.getDayOfWeek());
    workDay.setEdited(input.getEdited());
    workDay.setActive(input.getActive());
    workDay.setUser(UserFactory.createFrom(input.getUser()).orElse(null));
    return workDay;
  }

  public static WorkDayDTO toResponse(WorkDay domain) {
    log.info("toResponse, {}", domain);

    return new WorkDayDTO(
      domain.getId(),
      domain.getUuid(),
      domain.getDayOfWeek(),
      mapIfRequested(domain.hasAssociation(WorkDayAssociations.USER.name()), () -> toUserDTO(domain)),
      mapIfRequested(domain.hasAssociation(WorkDayAssociations.TIME_PERIOD.name()), () -> toTimePeriodDTOList(domain)),
      domain.isEdited(),
      domain.isActive()
    );
  }

  public static Set<WorkDayDTO> toResponseList(Set<WorkDay> list) {
    log.info("toResponseList, {}", list);

    return list.stream().map(WorkDayFactory::toResponse).collect(Collectors.toSet());
  }

  private static UserDTO toUserDTO(WorkDay workDay) {
    if (workDay.getUser() == null || !Hibernate.isInitialized(workDay.getUser())) return null;
    return UserFactory.toResponse(workDay.getUser());
  }

  private static Set<TimePeriodDTO> toTimePeriodDTOList(WorkDay workDay) {
    if (workDay.getShifts() == null || !Hibernate.isInitialized(workDay.getShifts())) return null;
    return TimePeriodFactory.toResponseList(workDay.getShifts());
  }
}
