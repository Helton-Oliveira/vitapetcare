package com.exampledigisphere.vitapetcare.admin.timePeriod;

import com.exampledigisphere.vitapetcare.admin.timePeriod.domain.TimePeriod;
import com.exampledigisphere.vitapetcare.config.root.Association;
import com.exampledigisphere.vitapetcare.config.root.AssociationFetcher;
import org.hibernate.Hibernate;

import java.util.function.Consumer;

public enum TimePeriodAssociations implements AssociationFetcher<TimePeriod>, Association {
  WORK_DAYS(timePeriod -> Hibernate.initialize(timePeriod.getWorkDay()));

  private final Consumer<TimePeriod> initializer;

  TimePeriodAssociations(Consumer<TimePeriod> initializer) {
    this.initializer = initializer;
  }

  @Override
  public void initialize(TimePeriod entity) {
    this.initializer.accept(entity);
  }

  @Override
  public String getName() {
    return this.name();
  }
}
