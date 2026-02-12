package com.exampledigisphere.vitapetcare.catalog.job;

import com.exampledigisphere.vitapetcare.admin.status.Status;
import com.exampledigisphere.vitapetcare.admin.user.domain.User;
import com.exampledigisphere.vitapetcare.auth.roles.Role;
import com.exampledigisphere.vitapetcare.config.root.BaseEntity;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.StringJoiner;

@Entity
@Table(name = "cat_jobs")
public class Job extends BaseEntity<Job> {
  public static final String TABLE_NAME = "cat_jobs";

  @NotBlank
  @JsonView(Json.List.class)
  private String name;

  @NotNull
  @JsonView(Json.Detail.class)
  private BigDecimal valueInCents;

  @NotNull
  @JsonView(Json.List.class)
  private BigInteger duration;

  @NotNull
  @Enumerated(EnumType.STRING)
  @JsonView(Json.Detail.class)
  private Role typesOfProfessionals;

  @NotNull
  @Enumerated(EnumType.STRING)
  @JsonView(Json.List.class)
  private JobType type = JobType.NOT_INFORMED;

  @NotNull
  @Enumerated(EnumType.STRING)
  @JsonView(Json.List.class)
  private Status status = Status.NOT_INFORMED;

  @NotNull
  @JsonView(Json.Detail.class)
  private Boolean hasReturn;

  @JsonView(Json.Detail.class)
  private Integer timeToReturnInDays;

  public Job() {
  }

  private Job(String name, BigDecimal valueInCents, BigInteger duration,
              Role typesOfProfessionals, JobType type, Status status, Boolean hasReturn, Integer timeToReturnInDays) {
    this.name = name;
    this.valueInCents = valueInCents;
    this.duration = duration;
    this.typesOfProfessionals = typesOfProfessionals;
    this.type = type;
    this.status = status;
    this.hasReturn = hasReturn;
    this.timeToReturnInDays = timeToReturnInDays;
  }

  public static Job create(String name, BigDecimal valueInCents, BigInteger duration,
                           Role typesOfProfessionals, JobType type, Status status, Boolean hasReturn, Integer timeToReturnInDays) {
    return new Job(
      name,
      valueInCents,
      duration,
      typesOfProfessionals,
      type,
      status,
      hasReturn,
      timeToReturnInDays
    );
  }

  public void convertDollarsInCents() {
    this.valueInCents = this.valueInCents.multiply(BigDecimal.valueOf(100));
  }

  public void convertCentsInDollars() {
    this.valueInCents = this.valueInCents.divideToIntegralValue(BigDecimal.valueOf(100));
  }

  public void convertHoursInMinutes() {
    this.duration = this.duration.multiply(BigInteger.valueOf(60));
  }

  public void convertMinutesInHours() {
    this.duration = this.duration.divide(BigInteger.valueOf(60));
  }

  public String getName() {
    return name;
  }

  public BigDecimal getValueInCents() {
    return valueInCents;
  }

  public BigInteger getDuration() {
    return duration;
  }

  public Role getTypesOfProfessional() {
    return typesOfProfessionals;
  }

  public JobType getType() {
    return type;
  }

  public Status getStatus() {
    return status;
  }

  public Boolean getHasReturn() {
    return hasReturn;
  }

  public Integer getTimeToReturnInDays() {
    return timeToReturnInDays;
  }

  public interface Json {
    interface List {
    }

    interface Detail extends List {
    }

    interface WithUser extends User.Json.Detail {
    }

    interface All extends Detail, WithUser {
    }
  }

  public interface Authority {
    String JOB_CREATE = "JOB_CREATE";
    String JOB_EDIT = "JOB_EDIT";
    String JOB_VIEW = "JOB_VIEW";
    String JOB_VIEW_LIST = "JOB_VIEW_LIST";
    String JOB_DELETE = "JOB_DELETE";
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", Job.class.getSimpleName() + "[", "]")
      .add("name='" + name + "'")
      .add("valueInCents=" + valueInCents)
      .add("duration=" + duration)
      //  .add("professionals=" + professionals)
      .add("type=" + type)
      .add("status=" + status)
      .toString();
  }
}
