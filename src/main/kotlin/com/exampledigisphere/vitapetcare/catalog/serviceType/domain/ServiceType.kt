package com.exampledigisphere.vitapetcare.catalog.serviceType.domain

import com.exampledigisphere.vitapetcare.admin.status.Status
import com.exampledigisphere.vitapetcare.config.root.BaseEntity
import com.exampledigisphere.vitapetcare.config.security.Permissions
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonView
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.hibernate.Hibernate

@Entity
@Table(name = "cat_services_types")
class ServiceType : BaseEntity() {

  final val TABLE_NAME = "cat_services_types";

  @field:NotBlank
  @field:JsonView(Json.List::class)
  var name: String? = null;

  @field:Enumerated(EnumType.STRING)
  @field:NotNull
  @field:JsonView(Json.List::class)
  var status: Status? = Status.NOT_INFORMED;

  @OneToMany(cascade = [(CascadeType.ALL)], fetch = FetchType.LAZY)
  @JsonIgnoreProperties("serviceType")
  @field:JsonView(Json.Detail::class)
  var services: MutableSet<ServiceType>? = null

  fun loadServices() = Hibernate.initialize(services)

  interface Json {
    interface List;
    interface Detail : List;
    interface All : Detail;
  }

  object ServiceTypePermissions : Permissions {
    override var resource = "SERVICE_TYPE";
  }

}
