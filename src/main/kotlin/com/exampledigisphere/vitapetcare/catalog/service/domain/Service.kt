package com.exampledigisphere.vitapetcare.catalog.service.domain

import com.exampledigisphere.vitapetcare.admin.status.Status
import com.exampledigisphere.vitapetcare.catalog.serviceType.domain.ServiceType
import com.exampledigisphere.vitapetcare.config.root.BaseEntity
import com.exampledigisphere.vitapetcare.config.security.Permissions
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.*
import org.hibernate.Hibernate
import java.math.BigDecimal

@Entity
@Table(name = "cat_service")
class Service : BaseEntity() {
  final val TABLE_NAME = "cat_service";

  var name: String? = null;
  var price: BigDecimal? = null;
  var description: String? = null;
  var status: Status? = Status.NOT_INFORMED;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "service_type_id")
  @JsonIgnoreProperties("services")
  var serviceType: ServiceType? = null;

  fun loadServiceType() = Hibernate.initialize(serviceType);

  interface Json {
    interface List;
    interface Detail : List;
    interface All : Detail;
  }

  object ServicePermissions : Permissions {
    override var resource = "SERVICE";
  }
}
