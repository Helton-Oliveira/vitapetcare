package com.exampledigisphere.vitapetcare.catalog.service.domain;

import com.exampledigisphere.vitapetcare.admin.status.Status;
import com.exampledigisphere.vitapetcare.catalog.serviceType.domain.ServiceType;
import com.exampledigisphere.vitapetcare.config.root.BaseEntity;
import com.exampledigisphere.vitapetcare.config.root.Info;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.math.BigDecimal;

@Entity
@Table(name = "cat_service")
@Getter
@Setter
@Info(
  dev = Info.Dev.heltonOliveira,
  label = Info.Label.doc,
  date = "29/12/2025",
  description = "Entidade que representa um serviço no catálogo"
)
public class Service extends BaseEntity {

  @Transient
  public static final String TABLE_NAME = "cat_service";

  private String name;
  private BigDecimal price;
  private String description;

  @Enumerated(EnumType.STRING)
  private Status status = Status.NOT_INFORMED;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "service_type_id")
  @JsonIgnoreProperties("services")
  private ServiceType serviceType;

  public Service loadServiceType() {
    Hibernate.initialize(serviceType);
    return this;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public ServiceType getServiceType() {
    return serviceType;
  }

  public void setServiceType(ServiceType serviceType) {
    this.serviceType = serviceType;
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
