package com.exampledigisphere.vitapetcare.admin.workDay;

import com.exampledigisphere.vitapetcare.admin.workDay.domain.WorkDay;
import com.exampledigisphere.vitapetcare.config.root.Association;
import com.exampledigisphere.vitapetcare.config.root.AssociationFetcher;
import org.hibernate.Hibernate;

import java.util.function.Consumer;

public enum WorkDayAssociations implements AssociationFetcher<WorkDay>, Association {
  USER(workDay -> Hibernate.initialize(workDay.getUser())),
  TIME_PERIOD(workDay -> Hibernate.initialize(workDay.getShifts()));

  private final Consumer<WorkDay> initializer;

  WorkDayAssociations(Consumer<WorkDay> initializer) {
    this.initializer = initializer;
  }

  @Override
  public void initialize(WorkDay entity) {
    this.initializer.accept(entity);
  }

  @Override
  public String getName() {
    return this.name();
  }
}
