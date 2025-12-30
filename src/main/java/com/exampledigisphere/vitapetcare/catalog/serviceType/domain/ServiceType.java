package com.exampledigisphere.vitapetcare.catalog.serviceType.domain;

import com.exampledigisphere.vitapetcare.admin.status.Status;
import com.exampledigisphere.vitapetcare.catalog.service.domain.Service;
import com.exampledigisphere.vitapetcare.config.root.BaseEntity;
import com.exampledigisphere.vitapetcare.config.root.Info;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cat_services_types")
@Getter
@Setter
@Info(
  dev = Info.Dev.heltonOliveira,
  label = Info.Label.doc,
  date = "29/12/2025",
  description = "Entidade que representa um tipo de serviço no catálogo"
)
public class ServiceType extends BaseEntity {

  @Transient
  public static final String TABLE_NAME = "cat_services_types";

  @NotBlank
  @JsonView(Json.List.class)
  private String name;

  @Enumerated(EnumType.STRING)
  @NotNull
  @JsonView(Json.List.class)
  private Status status = Status.NOT_INFORMED;

  @OneToMany(mappedBy = "serviceType", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JsonIgnoreProperties("serviceType")
  @JsonView(Json.Detail.class)
  private Set<Service> services = new HashSet<>();

  public ServiceType loadServices() {
    Hibernate.initialize(services);
    return this;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public Set<Service> getServices() {
    return services;
  }

  public void setServices(Set<Service> services) {
    this.services = services;
  }

  public interface Json {
    interface List {
    }

    interface Detail extends List {
    }

    interface All extends Detail {
    }
  }
}
